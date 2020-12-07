package com.example.themovieapp.views.home;

import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.themovieapp.models.entities.Movie;
import com.example.themovieapp.R;
import com.example.themovieapp.databinding.ActivityMainBinding;
import com.example.themovieapp.models.base.BaseActivity;
import com.example.themovieapp.models.entities.MovieDetails;
import com.example.themovieapp.presenters.MoviePresenter;
import com.example.themovieapp.views.home.Adapter.collections.MovieCollectionAdapter;
import com.example.themovieapp.views.home.Adapter.movies.MovieAdapter;
import com.example.themovieapp.views.movieDetail.MovieDetailsActivity;
import com.example.themovieapp.views.searchMovie.SearchActivity;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements MovieCollectionAdapter.MovieClickListener {
    private MoviePresenter moviePresenter = new MoviePresenter();
    private MovieAdapter movieAdapter = new MovieAdapter(this);
    CompositeDisposable compositeDisposable;
    private Toolbar toolbar;
    private RelativeLayout layout;
    private ImageButton img_btn_back;
    private TextView txt_app_name;
    private EditText edit_text_search;
    private ImageButton img_search;

    private static int PAGE = 1;
    public static String LANGUAGE = "en-US";
    private static String CATEGORY = "popular";
    private static String TOP_RATED = "top_rated";
    private static String UP_COMMIC = "upcoming";
    private static String API_KEY = "f464e68c8bda493269dc3dc20380ea29";

    @Override
    protected void init() {
        inflateView();
        initRV();
        showLoading();
        getPopular();
        getTopRated();
        getUpComic();
        setEvent();
    }

    private void inflateView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        compositeDisposable = new CompositeDisposable();
        layout = findViewById(R.id.relative_layout);
        img_btn_back = findViewById(R.id.img_btn_back);
        txt_app_name = findViewById(R.id.txt_app_name);
        edit_text_search = findViewById(R.id.edit_text_search);
        img_search = findViewById(R.id.img_search);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void setEvent() {
        movieAdapter.setMovieCallback(new MovieAdapter.MovieCallback() {
            @Override
            public void getMovieName(String movieName) {

            }
        });
        img_search.setOnClickListener(v -> {
            toolbar.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        });

        img_btn_back.setOnClickListener(v -> {
            toolbar.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            edit_text_search.setText("");
        });
        edit_text_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("search", edit_text_search.getText().toString());
                startActivity(intent);
                return true;
            }
            return false;

        });
    }

    private void getUpComic() {
        moviePresenter.getMovie(UP_COMMIC, API_KEY, LANGUAGE, PAGE, new MoviePresenter.getMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                movieAdapter.updateIncomming(movieList);
            }

            @Override
            public void onDetailMovieSuccess(MovieDetails movieDetails) {

            }

            @Override
            public void onFail(Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void getTopRated() {
        moviePresenter.getMovie(TOP_RATED, API_KEY, LANGUAGE, PAGE, new MoviePresenter.getMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                movieAdapter.updateTopRate(movieList);
            }

            @Override
            public void onDetailMovieSuccess(MovieDetails movieDetails) {

            }

            @Override
            public void onFail(Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void getPopular() {
        moviePresenter.getMovie(CATEGORY, API_KEY, LANGUAGE, PAGE, new MoviePresenter.getMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                hideLoading();
                movieAdapter.updatePopular(movieList);
            }

            @Override
            public void onDetailMovieSuccess(MovieDetails movieDetails) {

            }

            @Override
            public void onFail(Throwable t) {
                showToast(t.getMessage());
            }
        });
    }


    private void initRV() {
        LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        getBinding().rvMovieParent.setLayoutManager(firstManager);
        getBinding().rvMovieParent.setAdapter(movieAdapter);
    }
    @Override
    protected int setContentVewId() {
        return R.layout.activity_main;

    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("result", movie.id);
        startActivity(intent);
    }
}
