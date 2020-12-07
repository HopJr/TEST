package com.example.themovieapp.views.movieDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapp.R;
import com.example.themovieapp.databinding.MovieDetailsLayoutBinding;
import com.example.themovieapp.models.base.BaseActivity;
import com.example.themovieapp.models.entities.Movie;
import com.example.themovieapp.models.entities.MovieDetails;
import com.example.themovieapp.models.entities.ProductionCompany;
import com.example.themovieapp.models.retrofit.NetworkConstants;
import com.example.themovieapp.models.youtubeModel.YoutubeModel;
import com.example.themovieapp.presenters.MoviePresenter;
import com.example.themovieapp.views.company.CompanyAdapter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Picasso;
import com.google.android.youtube.player.YouTubeBaseActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsActivity extends YouTubeBaseActivity {
    private MoviePresenter moviePresenter = new MoviePresenter();
    private CompositeDisposable compositeDisposable, compositeDisposable_youtube;
    private ArrayList<ProductionCompany> mList = new ArrayList<>();
   // private CompanyAdapter adapter;
    private RecyclerView recyclerView;
    private MovieDetailsLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MovieDetailsLayoutBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        inflateView();
        initRCV();
        getParacable();
        addListeners();
    }

    private void initRCV() {
        recyclerView = binding.recyclerViewCompany;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        //adapter = new CompanyAdapter();
      //  recyclerView.setAdapter(adapter);
    }

    private void inflateView() {

        compositeDisposable_youtube = new CompositeDisposable();
        compositeDisposable = new CompositeDisposable();
        setSupportActionBar(binding.toolbar);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void getParacable() {
        long id = getIntent().getLongExtra("result", 100000);
        moviePresenter.getMovieDetail(id, NetworkConstants.API_KEY, NetworkConstants.LANGUAGE, new MoviePresenter.getMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movieList) {

            }

            @Override
            public void onDetailMovieSuccess(MovieDetails movieDetails) {
                fillData(movieDetails);
                binding.nestedScrollView.setVisibility(View.VISIBLE);
                getYoutubeTrailer(movieDetails);
            }

            @Override
            public void onFail(Throwable t) {
            }
        });
    }

    private void fillData(MovieDetails movieDetails) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movieDetails.getBackdrop_path()).into(binding.imgMovie);
        String year = movieDetails.getRelease_date();
        year = year.substring(0, year.indexOf("-"));
        binding.txtName.setText(movieDetails.getTitle() + " " + "(" + year + ")");
        binding.txtOverview.setText(movieDetails.getOverview());
        binding.txtOverviewStatus.setText("Overview");
        binding.txtStatus.setText("Status: " + movieDetails.getStatus());
        binding.txtOriginalLanguage.setText("Original Language: " + movieDetails.getOriginal_language());
        binding.txtRunTime.setText("Runtime: " + movieDetails.getRuntime() + " " + "min");
        String budget = String.valueOf(movieDetails.getBudget());
        double amount = Double.parseDouble(budget);
        DecimalFormat formatter = new DecimalFormat("#,###");
        binding.txtBudget.setText("Budget: " + formatter.format(amount));
        String revenue = String.valueOf(movieDetails.getRevenue());
        double amount_of_revenue = Double.parseDouble(revenue);
        binding.txtRevenue.setText("Revenue: " + formatter.format(amount_of_revenue));
        mList.addAll(movieDetails.getProduction_companies());
     //   adapter.updateCompanyAdapter(mList);
    }

    private void addListeners() {
        binding.imgBack.setOnClickListener(v -> finish());
    }

    private void getYoutubeTrailer(MovieDetails movieDetails) {
        moviePresenter.getYouTubeVideo(movieDetails.getOriginal_title() + " " + "trailer", youtubeModel -> loadYoutubeVideoToPlayer(youtubeModel));
    }

    private void loadYoutubeVideoToPlayer(final YoutubeModel youtubeModel) {
        binding.youtubePlayer.initialize(NetworkConstants.API_YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(youtubeModel.items.get(1).getId().getVideoId());
                youTubePlayer.pause();
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}




