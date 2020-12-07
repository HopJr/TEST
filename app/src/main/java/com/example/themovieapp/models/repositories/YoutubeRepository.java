package com.example.themovieapp.models.repositories;

import com.example.themovieapp.models.retrofit.NetworkConstants;
import com.example.themovieapp.models.retrofit.RetrofitClient;
import com.example.themovieapp.models.youtube.YoutubeApi;
import com.example.themovieapp.models.youtubeModel.YoutubeModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class YoutubeRepository {
    private YoutubeApi mService_youtube;
    private Retrofit retrofit_youtube;

    public YoutubeRepository() {
        retrofit_youtube = RetrofitClient.getInstance_youtube();
        mService_youtube = retrofit_youtube.create(YoutubeApi.class);
    }

    public void getYoutubeVideo(String url, GetYoutubeVideoCallBack callBack) {
        mService_youtube.searchYoutube("snippet", "video",
                NetworkConstants.API_YOUTUBE_KEY, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(youtubeModel -> callBack.onGetYoutubeVideoSuccess(youtubeModel), throwable -> {

                });
    }

    public interface GetYoutubeVideoCallBack {
        void onGetYoutubeVideoSuccess(YoutubeModel youtubeModel);
    }
}
