package com.example.themovieapp.models.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String BASE_YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/";
    public static Retrofit retrofit = null;
    public static Retrofit instance_youtube;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getInstance_youtube() {


        if (instance_youtube == null) {
            instance_youtube = new Retrofit.Builder()
                    .baseUrl(BASE_YOUTUBE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return instance_youtube;
    }
}
