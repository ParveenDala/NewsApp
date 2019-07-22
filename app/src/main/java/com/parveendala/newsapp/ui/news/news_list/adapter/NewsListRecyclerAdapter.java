package com.parveendala.newsapp.ui.news.news_list.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.parveendala.newsapp.R;
import com.parveendala.newsapp.data.NetworkState;
import com.parveendala.newsapp.data.News;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsListRecyclerAdapter extends PagedListAdapter<News, RecyclerView.ViewHolder> {
    private static final String TAG = "NewsListRecyclerAdapter";

    public static final int VIEW_TYPE_NETWORK_STATE = 0;
    public static final int VIEW_TYPE_NEWS = 1;

    private NetworkState networkState;
    private ItemClickListener itemClickListener;
    private RequestManager requestManager;

    public NewsListRecyclerAdapter(RequestManager requestManager) {
        super(DIFF_CALLBACK);
        this.requestManager = requestManager;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private boolean hasExtraRow() {
        return (networkState != null && networkState != NetworkState.SUCCESS);
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return VIEW_TYPE_NETWORK_STATE;
        } else {
            return VIEW_TYPE_NEWS;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_NEWS)
            return new NewsItemViewHolder(layoutInflater.inflate(R.layout.item_layout_news, parent, false), itemClickListener);
        else if (viewType == VIEW_TYPE_NETWORK_STATE)
            return new NetworkStateViewHolder(layoutInflater.inflate(R.layout.network_state_layout, parent, false), itemClickListener);
        else
            throw new IllegalArgumentException("Unknown ViewType");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsItemViewHolder)
            ((NewsItemViewHolder) holder).bindView(getItem(position), requestManager);
        else if (holder instanceof NetworkStateViewHolder)
            ((NetworkStateViewHolder) holder).bindView(networkState);
    }

    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    // use for ordering the items in view
    public static DiffUtil.ItemCallback<News> DIFF_CALLBACK = new DiffUtil.ItemCallback<News>() {
        @Override
        public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return (oldItem == newItem);
        }
    };
}
