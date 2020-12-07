package com.example.themovieapp.models.repositories;

import com.example.themovieapp.models.entities.Movie;
import com.example.themovieapp.models.entities.MovieDetails;
import com.example.themovieapp.models.entities.MoviesResponse;
import com.example.themovieapp.models.entities.SearchMovie;
import com.example.themovieapp.models.retrofit.NetworkConstants;
import com.example.themovieapp.models.retrofit.RetrofitClient;
import com.example.themovieapp.models.retrofit.ServicesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepositories {
    private static MovieRepositories instance;

    public MovieRepositories() {
    }

    public void requestApi(String category, String apiKey, String language, int page, RequestApiCallBack apiCallBack) {
        ServicesApi servicesApi = RetrofitClient.getClient().create(ServicesApi.class);
        Call<MoviesResponse> call = servicesApi.getMovie(category, apiKey, language, page);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                apiCallBack.onSuccess(movies);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                apiCallBack.onFail(t);
            }
        });
    }

    public void getMovieSearch(String query, int page, RequestApiCallBack apiCallBack) {
        ServicesApi servicesApi = RetrofitClient.getClient().create(ServicesApi.class);
        Call<SearchMovie> call = servicesApi.searchMovie(NetworkConstants.API_KEY, NetworkConstants.LANGUAGE, query, page);
        call.enqueue(new Callback<SearchMovie>() {
            @Override
            public void onResponse(Call<SearchMovie> call, Response<SearchMovie> response) {
                List<Movie> movies = response.body().getResults();
                apiCallBack.onSuccess(movies);
            }

            @Override
            public void onFailure(Call<SearchMovie> call, Throwable t) {
                apiCallBack.onFail(t);
            }
        });
    }

    public void getMovieDetail(long movieId, String apiKey, String language, RequestApiCallBack apiCallBack) {
        ServicesApi servicesApi = RetrofitClient.getClient().create(ServicesApi.class);
        Call<MovieDetails> call = servicesApi.getMovieDetails(movieId, apiKey, language);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails movieDetails = response.body();
                apiCallBack.onDetailMovieSuccess(movieDetails);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                apiCallBack.onFail(t);
            }
        });
    }

    public interface RequestApiCallBack {
        void onSuccess(List<Movie> movieList);

        void onDetailMovieSuccess(MovieDetails movieDetails);

        void onFail(Throwable throwable);
    }
}

