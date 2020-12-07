package com.example.themovieapp.presenters;

import com.example.themovieapp.models.entities.Movie;
import com.example.themovieapp.models.entities.MovieDetails;
import com.example.themovieapp.models.repositories.MovieRepositories;
import com.example.themovieapp.models.repositories.YoutubeRepository;
import com.example.themovieapp.models.youtubeModel.YoutubeModel;

import java.util.List;

public class MoviePresenter {
    private MovieRepositories movieRepositories = new MovieRepositories();
    private YoutubeRepository youtubeRepository = new YoutubeRepository();

    public void getMovie(String category, String apiKey, String language, int page, getMovieCallBack getMovieCallBack) {
        movieRepositories.requestApi(category, apiKey, language, page, new MovieRepositories.RequestApiCallBack() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                getMovieCallBack.onSuccess(movieList);
            }

            @Override
            public void onDetailMovieSuccess(MovieDetails movieDetails) {

            }

            @Override
            public void onFail(Throwable throwable) {
                getMovieCallBack.onFail(throwable);
            }
        });
    }

    public void getSearchMovie(String query, int page, getMovieCallBack movieCallBack) {
        movieRepositories.getMovieSearch(query, page, new MovieRepositories.RequestApiCallBack() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                movieCallBack.onSuccess(movieList);
            }

            @Override
            public void onDetailMovieSuccess(MovieDetails movieDetails) {

            }

            @Override
            public void onFail(Throwable throwable) {
                movieCallBack.onFail(throwable);
            }
        });
    }

    public void getMovieDetail(long movieId, String apiKey, String language, getMovieCallBack callBack) {
        movieRepositories.getMovieDetail(movieId, apiKey, language, new MovieRepositories.RequestApiCallBack() {
            @Override
            public void onSuccess(List<Movie> movieList) {

            }

            @Override
            public void onDetailMovieSuccess(MovieDetails movieDetails) {
                callBack.onDetailMovieSuccess(movieDetails);
            }

            @Override
            public void onFail(Throwable throwable) {
                callBack.onFail(throwable);
            }
        });
    }

    public void getYouTubeVideo(String url, GetVideoCallBack callBack) {
        youtubeRepository.getYoutubeVideo(url, youtubeModel -> callBack.onSuccess(youtubeModel));
    }

    public interface getMovieCallBack {
        void onSuccess(List<Movie> movieList);

        void onDetailMovieSuccess(MovieDetails movieDetails);

        void onFail(Throwable t);
    }

    public interface GetVideoCallBack {
        void onSuccess(YoutubeModel youtubeModel);
    }
}