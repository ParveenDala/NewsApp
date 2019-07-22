package com.parveendala.newsapp.ui.news.news_list.adapter;

import com.parveendala.newsapp.data.News;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public interface ItemClickListener {

    void onItemClicked(News article);

    void onRetryBtnClicked();
}
