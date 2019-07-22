package com.parveendala.newsapp.di;

import android.app.Application;

import com.parveendala.newsapp.NewsApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class
        , ActivityBuildersModule.class, AppModule.class, ViewModelFactoryModule.class})
interface AppComponent extends AndroidInjector<NewsApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
