package com.parveendala.newsapp.di;

import android.app.Application;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parveendala.newsapp.BuildConfig;
import com.parveendala.newsapp.R;
import com.parveendala.newsapp.database.NewsDatabase;
import com.parveendala.newsapp.util.Constants;
import com.parveendala.newsapp.util.DateDeserializer;
import com.parveendala.newsapp.util.NetworkUtil;

import java.io.IOException;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Module
public class AppModule {
    private static final String TAG = "AppModule";

    @Singleton
    @Provides
    static NetworkUtil provideNetworkUtil(Application application) {
        return new NetworkUtil(application);
    }

    @Singleton
    @Provides
    static NewsDatabase provideNewsDatabase(Application application) {
        return Room.databaseBuilder(application, NewsDatabase.class, NewsDatabase.DATA_BASE_NAME).build();
    }

    @Singleton
    @Provides
    static Gson provideGson() {
        return new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
    }

    @Singleton
    @Provides
    static GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    public OkHttpClient okHttpClient(Application application) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("x-api-key", BuildConfig.API_KEY)
                        .build();

                return chain.proceed(request);
            }
        }).addInterceptor(interceptor).build();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory).build();
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return new RequestOptions()
                .placeholderOf(R.drawable.image_bg_round)
                .transform(new CenterCrop(), new RoundedCorners(40));
    }

    @Singleton
    @Provides
    static RequestManager provideRequestManager(Application application, RequestOptions requestOptions) {
        return Glide.with(application).setDefaultRequestOptions(requestOptions);
    }
}
