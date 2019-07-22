package com.parveendala.newsapp.repository.net.api;

import com.parveendala.newsapp.data.News;

import java.util.List;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NewsResponse {
    private final String status;
    private final String message;
    private final int totalResults;
    private final List<News> articles;

    public NewsResponse(String status, String message, int totalResults, List<News> articles) {
        this.status = status;
        this.message = message;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<News> getArticles() {
        return articles;
    }
}
