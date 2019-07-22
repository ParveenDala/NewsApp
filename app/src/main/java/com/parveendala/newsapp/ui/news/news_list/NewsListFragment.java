package com.parveendala.newsapp.ui.news.news_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.RequestManager;
import com.parveendala.newsapp.R;
import com.parveendala.newsapp.data.News;
import com.parveendala.newsapp.ui.news.details.NewsDetailsFragment;
import com.parveendala.newsapp.ui.news.details.NewsDetailsViewModel;
import com.parveendala.newsapp.ui.news.news_list.adapter.ItemClickListener;
import com.parveendala.newsapp.ui.news.news_list.adapter.NewsListRecyclerAdapter;
import com.parveendala.newsapp.util.NetworkUtil;
import com.parveendala.newsapp.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsListFragment extends DaggerFragment implements ItemClickListener {
    private static final String TAG = "NewsListFragment";

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private NewsListViewModel newsListViewModel;

    private NewsDetailsViewModel newsDetailsViewModel;

    @Inject
    NewsListRecyclerAdapter recyclerAdapter;

    //Views
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView tvError;
    private Button btnRetry;
    private ProgressBar progressBar;

    @Inject
    public NewsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_list_fragment, container, false);
    }

    private void initViews(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        tvError = view.findViewById(R.id.error);
        progressBar = view.findViewById(R.id.progress_bar);
        btnRetry = view.findViewById(R.id.btn_retry);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetryBtnClicked();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter.setItemClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        newsListViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(NewsListViewModel.class);
        newsDetailsViewModel = ViewModelProviders.of(getActivity(), viewModelProviderFactory).get(NewsDetailsViewModel.class);
        observeNews();
    }

    private void observeNews() {
        newsListViewModel.getNews().observe(this, new Observer<PagedList<News>>() {
            @Override
            public void onChanged(PagedList<News> articles) {
                swipeRefreshLayout.setRefreshing(false);
                recyclerAdapter.submitList(articles);
            }
        });

        newsListViewModel.getNetworkState().observe(this, networkState -> {
            if ((recyclerAdapter.getItemCount() == 0) && networkState != null) {
                switch (networkState.getStatus()) {
                    case LOADING:
                        showLoading(true);
                        break;
                    case SUCCESS:
                        showLoading(false);
                        break;
                    case DATABASE:
                        showLoading(false);
                        break;
                    case END_OF_THE_LIST:
                        break;
                    case ERROR:
                    case INITIAL_ERROR:
                        showError(networkState.getMessage());
                        break;
                }
            } else {
                showLoading(false);
            }
            recyclerAdapter.setNetworkState(networkState);
        });
    }

    private void refreshNews() {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            newsListViewModel.refresh();
            showLoading(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void showLoading(boolean isShowing) {
        if (isShowing) {
            progressBar.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            tvError.setVisibility(View.GONE);
            btnRetry.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            btnRetry.setVisibility(View.GONE);
        }
    }

    private void showError(String message) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
        tvError.setText(message);
    }

    @Override
    public void onItemClicked(News article) {
        try {
            newsDetailsViewModel.getNews().postValue(article);
            if (!newsDetailsViewModel.getNews().hasActiveObservers()) {
                NewsDetailsFragment detailsFragment = new NewsDetailsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRetryBtnClicked() {
        if (recyclerAdapter.getNetworkState() != null) {
            switch (recyclerAdapter.getNetworkState().getStatus()) {
                case INITIAL_ERROR:
                case DATABASE:
                    refreshNews();
                    break;
                default:
                    newsListViewModel.retry();
                    break;
            }
        }
    }
}
