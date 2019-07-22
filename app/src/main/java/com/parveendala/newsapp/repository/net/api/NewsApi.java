package com.parveendala.newsapp.repository.net.api;

import com.parveendala.newsapp.util.Constants;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public interface NewsApi {

    //India
    @GET("/v2/top-headlines?country=in")
    Single<NewsResponse> getNewsHeadlines(
            @Query("page") int page,
            @Query("pageSize") int pageSize);


    @Headers("x-api-key: " + Constants.API_KEY)
    @GET("/v2/top-headlines")
    Call<NewsResponse> getTopHeadlines2(
            @Query("country") String country
            , @Query("page") int page, @Query("pageSize") int pageSize);

}
