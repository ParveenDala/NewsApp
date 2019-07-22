package com.parveendala.newsapp.ui.news.news_list.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parveendala.newsapp.R;
import com.parveendala.newsapp.data.NetworkState;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NetworkStateViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NetworkStateViewHolder";
    private TextView tvError;
    private Button btnRetry;
    private ProgressBar progressBar;

    public NetworkStateViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener) {
        super(itemView);
        tvError = itemView.findViewById(R.id.error);
        btnRetry = itemView.findViewById(R.id.btn_retry);
        progressBar = itemView.findViewById(R.id.progress_bar);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onRetryBtnClicked();
            }
        });
    }

    public void bindView(NetworkState networkState) {
        if (networkState != null) {
            progressBar.setVisibility(View.GONE);
            btnRetry.setVisibility(View.GONE);
            tvError.setVisibility(View.GONE);
            tvError.setText(networkState.getMessage());
            switch (networkState.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case DATABASE:
                    tvError.setVisibility(View.VISIBLE);
                    btnRetry.setVisibility(View.VISIBLE);
                    break;
                case END_OF_THE_LIST:
                    tvError.setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    tvError.setVisibility(View.VISIBLE);
                    btnRetry.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
