package com.parveendala.newsapp.ui.news.news_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.parveendala.newsapp.data.NetworkState;
import com.parveendala.newsapp.data.News;
import com.parveendala.newsapp.repository.NewsRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsListViewModel extends ViewModel {
    private static final String TAG = "NewsListViewModel";

    @Inject
    NewsRepository newsRepository;
    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    public NewsListViewModel() {
    }

    public void refresh() {
        newsRepository.refresh();
    }

    public LiveData<PagedList<News>> getNews() {
        return newsRepository.getNews();
    }

    public LiveData<NetworkState> getNetworkState() {
        return newsRepository.getNetworkState();
    }

    public void retry() {
        newsRepository.retry();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}