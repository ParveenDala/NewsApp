package com.parveendala.newsapp.repository.net.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.parveendala.newsapp.data.NetworkState;
import com.parveendala.newsapp.data.News;
import com.parveendala.newsapp.repository.net.api.NewsApi;
import com.parveendala.newsapp.repository.net.api.NewsResponse;
import com.parveendala.newsapp.util.Constants;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsPageKeyedDataSource extends PageKeyedDataSource<String, News> {
    private static final String TAG = "NewsNetworkPageKeyedDat";
    private final static int FIRST_PAGE = 1;

    private final NewsApi newsApi;
    private Completable retryCompletable;
    private CompositeDisposable compositeDisposable;

    private MutableLiveData networkState;
    private final ReplaySubject<News> newsObservable;

    public NewsPageKeyedDataSource(CompositeDisposable compositeDisposable, NewsApi newsApi) {
        this.newsApi = newsApi;
        this.compositeDisposable = compositeDisposable;
        networkState = new MutableLiveData();
        newsObservable = ReplaySubject.create();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<News> getSingleNews() {
        return newsObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, News> callback) {
        Log.d(TAG, "LoadInitialParams " + params.requestedLoadSize);
        networkState.postValue(NetworkState.LOADING);
        newsApi.getNewsHeadlines(FIRST_PAGE, Constants.LOADING_PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(NewsResponse response) {
                        networkState.postValue(NetworkState.SUCCESS);
                        callback.onResult(response.getArticles(), null, String.valueOf(FIRST_PAGE + 1));
                        for (News article : response.getArticles()) {
                            newsObservable.onNext(article);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //networkState.postValue(new NetworkState(NetworkState.Status.INITIAL_ERROR, e.getLocalizedMessage()));
                        callback.onResult(new ArrayList<>(), null, String.valueOf(FIRST_PAGE));
                        //setRetryCompletable(() -> loadInitial(params, callback));
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, News> callback) {
        Log.d(TAG, "loadAfter: " + params.key);

        networkState.postValue(NetworkState.LOADING);
        newsApi.getNewsHeadlines(Integer.valueOf(params.key), Constants.LOADING_PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(NewsResponse response) {
                        networkState.postValue(NetworkState.SUCCESS);
                        callback.onResult(response.getArticles(), Integer.toString(Integer.valueOf(params.key) + 1));
                        for (News article : response.getArticles()) {
                            newsObservable.onNext(article);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        networkState.postValue(new NetworkState(NetworkState.Status.ERROR, e.getLocalizedMessage()));
                        setRetryCompletable(() -> loadAfter(params, callback));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, News> callback) {
        Log.d(TAG, "loadBefore: " + params.key);
    }

    public void retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }
    }

    private void setRetryCompletable(Action action) {
        if (action != null)
            retryCompletable = Completable.fromAction(action);
        else retryCompletable = null;
    }

}