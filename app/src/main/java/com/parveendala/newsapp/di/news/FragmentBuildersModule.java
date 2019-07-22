package com.parveendala.newsapp.di.news;

import com.parveendala.newsapp.repository.db.NewsDb;
import com.parveendala.newsapp.ui.news.details.NewsDetailsFragment;
import com.parveendala.newsapp.ui.news.news_list.NewsListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Module
public abstract class FragmentBuildersModule {


    @ContributesAndroidInjector
    abstract NewsListFragment provideNewsListFragment();


    @ContributesAndroidInjector
    abstract NewsDetailsFragment provideNewsDetailsFragment();

}
