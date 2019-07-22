package com.parveendala.newsapp.ui.news.news_list.adapter;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.parveendala.newsapp.R;
import com.parveendala.newsapp.data.News;

import java.util.Calendar;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsItemViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NewsItemViewHolder";
    private ImageView ivImage;
    private TextView tvTitle, tvTime;
    private News news;


    public NewsItemViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener) {
        super(itemView);
        ivImage = itemView.findViewById(R.id.image);
        tvTitle = itemView.findViewById(R.id.title);
        tvTime = itemView.findViewById(R.id.time);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onItemClicked(news);
            }
        });
    }

    public void bindView(News news, RequestManager requestManager) {
        this.news = news;
        if (news != null) {
            requestManager.load(news.getImageUrl()).into(ivImage);
            tvTitle.setText(news.getTitle());
            if (news.getPublishedAt() != null) {
                tvTime.setText(DateUtils.getRelativeTimeSpanString(news.getPublishedAt().getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
            } else {
                tvTime.setText("NA");
            }
        } else {
            requestManager.load(R.drawable.image_bg_round).into(ivImage);
            tvTitle.setText("NA");
            tvTime.setText("NA");
        }
    }
}
