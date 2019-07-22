package com.parveendala.newsapp.repository.db;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.parveendala.newsapp.database.NewsDao;

import javax.inject.Inject;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsDbDataSourceFactory extends DataSource.Factory {
    private static final String TAG = "NewsDbDataSourceFactory";

    private NewsDao newsDao;

    @Inject
    public NewsDbDataSourceFactory(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @NonNull
    @Override
    public DataSource create() {
        return new NewsDbPageKeyedDataSource(newsDao);
    }
}
