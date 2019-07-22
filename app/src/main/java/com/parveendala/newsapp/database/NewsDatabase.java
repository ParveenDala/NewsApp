package com.parveendala.newsapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.parveendala.newsapp.data.News;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Database(entities = News.class, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static final String TAG = "NewsDatabase";
    public static final String DATA_BASE_NAME = "news_database.db";

    NewsDatabase() {

    }

    public abstract NewsDao newsDao();
}
