package com.example.themovieapp.models.retrofit;

import com.example.themovieapp.models.entities.MovieDetails;
import com.example.themovieapp.models.entities.MoviesResponse;
import com.example.themovieapp.models.entities.SearchMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicesApi {
    @GET("/3/movie/{category}")
    Call<MoviesResponse> getMovie(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("/3/search/movie/")
    Call<SearchMovie> searchMovie(@Query("api_key") String api_key,
                                  @Query("language") String language,
                                  @Query("query") String query,
                                  @Query("page") int page);


    @GET("/3/movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") long movieId,
                                       @Query("api_key") String api_key,
                                       @Query("language") String language);
}
