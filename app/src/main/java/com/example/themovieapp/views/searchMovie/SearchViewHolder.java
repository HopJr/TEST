package com.example.themovieapp.views.searchMovie;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapp.R;
import com.example.themovieapp.databinding.SearchItemBinding;
import com.example.themovieapp.models.entities.Movie;
import com.squareup.picasso.Picasso;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private SearchItemBinding binding;
    private TextView txt_title, txt_raiting;
    private ImageView img_movie;
    private RatingBar ratingBar;
    private SearchAdapter.MovieClickListener clickListener;

    public SearchViewHolder(@NonNull SearchItemBinding searchLayoutBinding, SearchAdapter.MovieClickListener listener) {
        super(searchLayoutBinding.getRoot());
        this.binding = searchLayoutBinding;
        txt_title = itemView.findViewById(R.id.txt_name);
        img_movie = itemView.findViewById(R.id.img_movie);
        ratingBar = itemView.findViewById(R.id.ratingBar);
        txt_raiting = itemView.findViewById(R.id.txt_raiting);
        this.clickListener = listener;

    }

    public void onBind(Movie movie) {
        double avrage_count = movie.getVote_average();
        avrage_count = avrage_count / 2;
        ratingBar.setRating((float) avrage_count);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FA9900"), PorterDuff.Mode.SRC_ATOP);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path())
                .into(img_movie);
        txt_title.setText(movie.getTitle());
        txt_raiting.setText("" + movie.getVote_average());
        itemView.setOnClickListener(v -> {
            clickListener.onMovieClick(movie);
        });
    }
}
