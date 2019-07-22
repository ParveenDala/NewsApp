package com.parveendala.newsapp.di;

import androidx.lifecycle.ViewModelProvider;

import com.parveendala.newsapp.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

}
