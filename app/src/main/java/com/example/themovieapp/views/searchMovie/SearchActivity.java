package com.example.themovieapp.views.searchMovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapp.R;
import com.example.themovieapp.databinding.SearchLayoutBinding;
import com.example.themovieapp.models.Helper.RecyclerTouchListener;
import com.example.themovieapp.models.base.BaseActivity;
import com.example.themovieapp.models.entities.Movie;
import com.example.themovieapp.models.entities.MovieDetails;
import com.example.themovieapp.models.entities.SearchMovie;
import com.example.themovieapp.models.retrofit.NetworkConstants;
import com.example.themovieapp.models.retrofit.RetrofitClient;
import com.example.themovieapp.models.retrofit.ServicesApi;
import com.example.themovieapp.presenters.MoviePresenter;
import com.example.themovieapp.views.movieDetail.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends BaseActivity<SearchLayoutBinding> implements SearchAdapter.MovieClickListener {
    MoviePresenter moviePresenter = new MoviePresenter();
    private RecyclerView recyclerView;
    private SearchAdapter adapter = new SearchAdapter(this);
    private ArrayList<Movie> mList;
    private EditText edit_text_search;
    private ImageButton img_btn_back;
    String search;
    int page = 1;

    @Override
    protected void init() {
        inflateView();
        initRCV();
        getData();
        addListener();
    }


    @Override
    protected int setContentVewId() {
        return R.layout.search_layout;
    }

    private void initRCV() {
        mList = new ArrayList<>();
        recyclerView = getBinding().recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        search = getIntent().getStringExtra("search");
        edit_text_search.setText(search);
        moviePresenter.getSearchMovie(search, page, new MoviePresenter.getMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                adapter.updateAdapter(movieList);
            }

            @Override
            public void onDetailMovieSuccess(MovieDetails movieDetails) {

            }

            @Override
            public void onFail(Throwable t) {

            }
        });
    }

    private void inflateView() {
        img_btn_back = findViewById(R.id.img_btn_back);
        edit_text_search = findViewById(R.id.edit_text_search);
    }


    private void addListener() {
        edit_text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    return true;
                }
                return false;
            }
        });


        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("result", movie.id);
        startActivity(intent);
    }
//    private void getSearchInfromation() {
//        compositeDisposable.add(mService.searchMovie(NetworkConstants.API_KEY, NetworkConstants.LANGUAGE,
//                edit_text_search.getText().toString(), page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<SearchMovie>() {
//                    @Override
//                    public void accept(SearchMovie searchMovie) throws Exception {
//                        mList.clear();
//                        mList.addAll(searchMovie.getResults());
//                        for (int i = 0; i < mList.size(); i++) {
//                            if (mList.get(i).getPoster_path() == null) {
//                                mList.remove(i);
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                }));
//    }
}
