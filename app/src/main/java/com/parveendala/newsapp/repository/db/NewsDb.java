package com.parveendala.newsapp.repository.db;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.parveendala.newsapp.data.News;
import com.parveendala.newsapp.database.NewsDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsDb {
    private static final String TAG = "NewsDb";

    private NewsDao newsDao;
    private LiveData<PagedList<News>> newsPagedLiveData;

    @Inject
    public NewsDb(NewsDao newsDao, NewsDbDataSourceFactory dataSourceFactory) {
        this.newsDao = newsDao;
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setPageSize(Integer.MAX_VALUE)
                .setInitialLoadSizeHint(Integer.MAX_VALUE)
                .setEnablePlaceholders(false)
                .build();

        Executor executor = Executors.newFixedThreadPool(3);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        newsPagedLiveData = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<News>> getNews() {
        return newsPagedLiveData;
    }

    public void insertNews(News news) {
        newsDao.insertNews(news);
    }

    public void insertNews(List<News> news) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                newsDao.insertNews(news);
                return null;
            }
        }.execute();
    }
}

