package com.example.themovieapp.models.youtube;


import com.example.themovieapp.models.youtubeModel.YoutubeModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeApi {
    @GET("search")
    Observable<YoutubeModel> searchYoutube(@Query("part") String part,
                                           @Query("type") String type,
                                           @Query("key") String key,
                                           @Query("q") String q);
}
