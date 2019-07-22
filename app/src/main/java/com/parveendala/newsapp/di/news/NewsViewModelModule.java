package com.parveendala.newsapp.di.news;

import androidx.lifecycle.ViewModel;

import com.parveendala.newsapp.di.ViewModelKey;
import com.parveendala.newsapp.ui.news.details.NewsDetailsViewModel;
import com.parveendala.newsapp.ui.news.news_list.NewsListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Module
public abstract class NewsViewModelModule {

    @NewsScope
    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel.class)
    public abstract ViewModel provideNewsListViewModel(NewsListViewModel newsListViewModel);


    @NewsScope
    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel.class)
    public abstract ViewModel provideNewsDetailsViewModel(NewsDetailsViewModel newsDetailsViewModel);

}
