package com.parveendala.newsapp;

import com.parveendala.newsapp.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
