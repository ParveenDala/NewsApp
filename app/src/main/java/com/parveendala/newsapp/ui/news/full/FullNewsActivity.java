package com.parveendala.newsapp.ui.news.full;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parveendala.newsapp.R;
import com.parveendala.newsapp.util.Constants;
import com.parveendala.newsapp.util.NetworkUtil;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class FullNewsActivity extends DaggerAppCompatActivity {

    @Inject
    NetworkUtil networkUtil;

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.svg_back);
        webView = findViewById(R.id.web_view);

        String url = null;
        if (getIntent() != null) {
            url = getIntent().getStringExtra(Constants.NEWS_URL);
        }

        if (url != null)
            loadNews(url);
        else
            onError();
    }

    private void loadNews(String url) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("about:blank");
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDomStorageEnabled(true);
        if (networkUtil.isNetworkAvailable()) {
            webView.loadUrl(url);
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        } else {
            webView.loadData("<html><body style='text-align:center;'><br><br><h2>Connection Error!</h2><br><h4>Please check your internet connection</h4></body></html>", "text/html", null);
        }
    }

    private void onError() {
        Toast.makeText(FullNewsActivity.this, R.string.msg_something_wrong, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();

        if (!webView.canGoBack())
            super.onBackPressed();
    }
}
