package com.parveendala.newsapp.ui.news;

import android.os.Bundle;

import com.parveendala.newsapp.R;
import com.parveendala.newsapp.ui.news.news_list.NewsListFragment;

import dagger.android.support.DaggerAppCompatActivity;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsActivity extends DaggerAppCompatActivity {
    private static final String TAG = "NewsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(null);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            NewsListFragment newsListFragment = new NewsListFragment();
            newsListFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, newsListFragment).commit();
        }
    }
}
