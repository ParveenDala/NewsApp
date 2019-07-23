package com.parveendala.newsapp.repository.net.api;

import com.parveendala.newsapp.util.Constants;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public interface NewsApi {

    //India
    @GET("/v2/top-headlines?country=in")
    Single<NewsResponse> getNewsHeadlines(
            @Query(Constants.PAGE) int page,
            @Query(Constants.PAGE_SIZE) int pageSize);
}
