package com.parveendala.newsapp.di;

import com.parveendala.newsapp.di.news.FragmentBuildersModule;
import com.parveendala.newsapp.di.news.NewsModule;
import com.parveendala.newsapp.di.news.NewsScope;
import com.parveendala.newsapp.di.news.NewsViewModelModule;
import com.parveendala.newsapp.ui.news.NewsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Module
public abstract class ActivityBuildersModule {

    @NewsScope
    @ContributesAndroidInjector(modules = {NewsModule.class, NewsViewModelModule.class, FragmentBuildersModule.class})
    public abstract NewsActivity provideNewsActivity();

}