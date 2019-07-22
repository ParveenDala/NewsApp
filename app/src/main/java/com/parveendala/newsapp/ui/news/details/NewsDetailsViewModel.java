package com.parveendala.newsapp.ui.news.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.parveendala.newsapp.data.News;

import javax.inject.Inject;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsDetailsViewModel extends ViewModel {
    final private MutableLiveData<News> newsMutableLiveData;

    @Inject
    public NewsDetailsViewModel() {
        newsMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<News> getNews() {
        return newsMutableLiveData;
    }

}
