package com.parveendala.newsapp.ui.news.details;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.parveendala.newsapp.R;
import com.parveendala.newsapp.data.News;
import com.parveendala.newsapp.ui.news.full.FullNewsActivity;
import com.parveendala.newsapp.util.Constants;
import com.parveendala.newsapp.viewmodels.ViewModelProviderFactory;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsDetailsFragment extends DaggerFragment {
    private static final String TAG = "NewsDetailsFragment";

    @Inject
    RequestManager requestManager;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private NewsDetailsViewModel newsDetailsViewModel;
    private News news;

    //Views
    private View mainLayout;
    private ImageView ivImage;
    private Button btnReadFull;
    private TextView tvTitle, tvSource, tvTime, tvDescription, tvContent;

    @Inject
    public NewsDetailsFragment() {
        Log.d(TAG, "NewsDetailsFragment: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_details_fragment, container, false);
    }

    private void initViews(View view) {
        mainLayout = view.findViewById(R.id.main_layout);
        mainLayout.setVisibility(View.GONE);
        ivImage = view.findViewById(R.id.image);
        tvTitle = view.findViewById(R.id.title);
        tvSource = view.findViewById(R.id.source);
        tvTime = view.findViewById(R.id.time);
        tvDescription = view.findViewById(R.id.description);
        tvContent = view.findViewById(R.id.content);
        btnReadFull = view.findViewById(R.id.btn_read_full);
        btnReadFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFullArticle();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        newsDetailsViewModel = ViewModelProviders.of(getActivity(), viewModelProviderFactory).get(NewsDetailsViewModel.class);
        observeNews();
    }

    private void observeNews() {
        newsDetailsViewModel.getNews().observe(this, new Observer<News>() {
            @Override
            public void onChanged(News updated) {
                news = updated;
                setData();
            }
        });
    }

    private void setData() {
        if (news != null) {
            try {
                requestManager.load(news.getImageUrl()).placeholder(R.drawable.image_bg).centerCrop().into(ivImage);
                tvTitle.setText(news.getTitle());

                if (news.getPublishedAt() != null) {
                    tvTime.setText(DateUtils.getRelativeTimeSpanString(news.getPublishedAt().getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
                } else {
                    tvTime.setText("NA");
                }
                tvDescription.setText(news.getDescription());
                tvContent.setText(news.getContent());
                mainLayout.setVisibility(View.VISIBLE);
                if (news.getSource() != null)
                    tvSource.setText(news.getSource().getName());
            } catch (Exception ex) {
                ex.printStackTrace();
                mainLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.msg_something_wrong, Toast.LENGTH_SHORT).show();
            }
        } else {
            mainLayout.setVisibility(View.GONE);
        }
    }

    private void readFullArticle() {
        Intent intent = new Intent(getActivity(), FullNewsActivity.class);
        intent.putExtra(Constants.NEWS_URL, news.getUrl());
        startActivity(intent);
    }
}
