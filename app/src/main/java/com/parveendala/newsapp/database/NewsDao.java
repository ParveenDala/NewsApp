package com.parveendala.newsapp.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.parveendala.newsapp.data.News;

import java.util.List;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Dao
public interface NewsDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNews(News news);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNews(List<News> newsList);

    @Query("SELECT * FROM News")
    DataSource.Factory<Integer, News> getAllPagedNews();

    @Query("SELECT * FROM News")
    List<News> getNews();

    @Query("DELETE FROM News")
    void deleteAllNews();

}
