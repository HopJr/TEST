package com.example.themovieapp.views.home.Adapter.collections;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapp.databinding.MovieCardBinding;
import com.example.themovieapp.models.entities.Movie;
import com.squareup.picasso.Picasso;
import com.example.themovieapp.views.home.Adapter.collections.MovieCollectionAdapter.MovieClickListener;

public class MovieCollectionViewHolder extends RecyclerView.ViewHolder {
    private MovieCardBinding binding;
    private MovieClickListener listener;


    public MovieCollectionViewHolder(@NonNull MovieCardBinding binding, MovieClickListener movieClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = movieClickListener;
    }

    public void binding(Movie movie) {
        binding.tvTitle.setText(movie.getOriginal_title());
        String vote = Double.toString(movie.getVote_average());
        binding.tvUserRating.setText(vote);
        String poster = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
        Picasso.get().load(movie.getMoviePath(movie.getPoster_path())).into(binding.ivThumbnail);
        itemView.setOnClickListener(v -> listener.onMovieClick(movie));
    }
}
