package com.parveendala.newsapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.parveendala.newsapp.data.NetworkState;
import com.parveendala.newsapp.data.News;
import com.parveendala.newsapp.repository.db.NewsDb;
import com.parveendala.newsapp.repository.net.paging.NewsDataSourceFactory;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

import static com.parveendala.newsapp.util.Constants.INITIAL_PAGES;
import static com.parveendala.newsapp.util.Constants.LOADING_PAGE_SIZE;

public class NewsRepository {
    private static final String TAG = "NewsRepository";

    private final LiveData<PagedList<News>> newsPagedLiveData;
    private final MediatorLiveData<PagedList<News>> liveDataMerger = new MediatorLiveData<>();
    private final MediatorLiveData<NetworkState> networkStateMediatorLiveData = new MediatorLiveData();

    private NewsDb newsDb;
    private NewsDataSourceFactory dataSourceFactory;

    @Inject
    public NewsRepository(NewsDataSourceFactory dataSourceFactory, NewsDb newsDb) {
        this.dataSourceFactory = dataSourceFactory;
        this.newsDb = newsDb;

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(INITIAL_PAGES)
                .setPageSize(LOADING_PAGE_SIZE)
                .build();

        LiveData<NetworkState> networkStateLiveData = Transformations.switchMap(dataSourceFactory.getNewsDataSourceLiveData(),
                dataSource -> dataSource.getNetworkState());

        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        newsPagedLiveData = livePagedListBuilder.
                setFetchExecutor(Executors.newFixedThreadPool(3)).
                setBoundaryCallback(boundaryCallback).
                build();


        liveDataMerger.addSource(newsPagedLiveData, new Observer<PagedList<News>>() {
            @Override
            public void onChanged(PagedList<News> articles) {
                liveDataMerger.setValue(articles);
            }
        });

        networkStateMediatorLiveData.addSource(networkStateLiveData
                , new Observer<NetworkState>() {
                    @Override
                    public void onChanged(NetworkState networkState) {
                        networkStateMediatorLiveData.setValue(networkState);
                    }
                });
    }

    private PagedList.BoundaryCallback<News> boundaryCallback = new PagedList.BoundaryCallback<News>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(newsDb.getNews(), value -> {
                liveDataMerger.setValue(value);
                liveDataMerger.removeSource(newsDb.getNews());
                if (value != null && value.size() > 0)
                    networkStateMediatorLiveData.setValue(NetworkState.DATABASE);
                else
                    networkStateMediatorLiveData.setValue(new NetworkState(NetworkState.Status.INITIAL_ERROR, null));
            });
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull News itemAtFront) {
            super.onItemAtFrontLoaded(itemAtFront);
            dataSourceFactory.getNews().observeOn(Schedulers.io())
                    .subscribe(news -> {
                        if (news != null)
                            newsDb.insertNews(news);
                    });
        }

        @Override
        public void onItemAtEndLoaded(@NonNull News itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);
            networkStateMediatorLiveData.setValue(NetworkState.END_OF_THE_LIST);
        }
    };

    public LiveData<PagedList<News>> getNews() {
        return liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkStateMediatorLiveData;
    }

    public void retry() {
        dataSourceFactory.retry();
    }

    public void refresh() {
        newsPagedLiveData.getValue().getDataSource().invalidate();
    }
}