package com.parveendala.newsapp.di.news;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.RequestManager;
import com.parveendala.newsapp.database.NewsDao;
import com.parveendala.newsapp.database.NewsDatabase;
import com.parveendala.newsapp.repository.NewsRepository;
import com.parveendala.newsapp.repository.db.NewsDb;
import com.parveendala.newsapp.repository.db.NewsDbDataSourceFactory;
import com.parveendala.newsapp.repository.net.api.NewsApi;
import com.parveendala.newsapp.repository.net.paging.NewsDataSourceFactory;
import com.parveendala.newsapp.ui.news.NewsActivity;
import com.parveendala.newsapp.ui.news.news_list.adapter.NewsListRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Module
public class NewsModule {
    private static final String TAG = "NewsModule";

    @NewsScope
    @Provides
    static NewsDao providesNewsDao(NewsDatabase newsDatabase) {
        return newsDatabase.newsDao();
    }

    @NewsScope
    @Provides
    static NewsDataSourceFactory providesNewsDataSourceFactory(CompositeDisposable compositeDisposable, NewsApi newsApi) {
        return new NewsDataSourceFactory(compositeDisposable, newsApi);
    }

    @NewsScope
    @Provides
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @NewsScope
    @Provides
    static NewsRepository provideNewsRepository(NewsDataSourceFactory dataSourceFactory, NewsDb newsDb) {
        return new NewsRepository(dataSourceFactory, newsDb);
    }

    @NewsScope
    @Provides
    static NewsApi provideNewsApi(Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }

    @NewsScope
    @Provides
    static NewsDb providesNewsDb(NewsDao newsDao, NewsDbDataSourceFactory dataSourceFactory) {
        return new NewsDb(newsDao, dataSourceFactory);
    }

    @Provides
    static NewsDbDataSourceFactory providesDbDataSourceFactory(NewsDao newsDao) {
        return new NewsDbDataSourceFactory(newsDao);
    }


    @NewsScope
    @Provides
    static LinearLayoutManager providesLinearLayoutManager(NewsActivity newsActivity) {
        return new LinearLayoutManager(newsActivity);
    }

    @NewsScope
    @Provides
    static NewsListRecyclerAdapter provideNewsListRecyclerAdapter(RequestManager requestManager) {
        return new NewsListRecyclerAdapter(requestManager);
    }

}
