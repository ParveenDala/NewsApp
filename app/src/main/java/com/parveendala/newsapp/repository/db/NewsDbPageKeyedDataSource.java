package com.parveendala.newsapp.repository.db;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.parveendala.newsapp.data.News;
import com.parveendala.newsapp.database.NewsDao;

import java.util.List;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsDbPageKeyedDataSource extends PageKeyedDataSource<String, News> {
    private static final String TAG = "NewsDbPageKeyedDataSour";
    private final NewsDao newsDao;

    public NewsDbPageKeyedDataSource(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, News> callback) {
        List<News> newsList = newsDao.getNews();
        if (newsList != null && newsList.size() > 0) {
            callback.onResult(newsList, null, "1");
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, News> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, News> callback) {
    }
}
