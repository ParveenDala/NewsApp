package com.parveendala.newsapp.repository.net.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.parveendala.newsapp.data.News;
import com.parveendala.newsapp.repository.net.api.NewsApi;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.ReplaySubject;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsDataSourceFactory extends DataSource.Factory {
    private static final String TAG = "NewsNetworkDataSourceFa";

    private MutableLiveData<NewsPageKeyedDataSource> newsDataSourceLiveData;
    private NewsPageKeyedDataSource newsNetworkPageKeyedDataSource;
    private CompositeDisposable compositeDisposable;
    private NewsApi newsApi;

    @Inject
    public NewsDataSourceFactory(CompositeDisposable compositeDisposable, NewsApi newsApi) {
        this.newsDataSourceLiveData = new MutableLiveData<>();
        this.compositeDisposable = compositeDisposable;
        this.newsApi = newsApi;
    }

    @NonNull
    @Override
    public DataSource create() {
        this.newsNetworkPageKeyedDataSource = new NewsPageKeyedDataSource(compositeDisposable, newsApi);
        newsDataSourceLiveData.postValue(newsNetworkPageKeyedDataSource);
        return newsNetworkPageKeyedDataSource;
    }

    public MutableLiveData<NewsPageKeyedDataSource> getNewsDataSourceLiveData() {
        return newsDataSourceLiveData;
    }

    public ReplaySubject<News> getNews() {
        if (newsNetworkPageKeyedDataSource != null)
            return newsNetworkPageKeyedDataSource.getSingleNews();
        else return null;
    }

    public void retry() {
        newsNetworkPageKeyedDataSource.retry();
    }
}
