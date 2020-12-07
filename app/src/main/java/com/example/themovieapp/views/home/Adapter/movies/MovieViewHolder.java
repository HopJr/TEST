package com.example.themovieapp.views.home.Adapter.movies;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapp.databinding.ItemMovieCollectionBinding;
import com.example.themovieapp.models.entities.Movie;
import com.example.themovieapp.views.home.Adapter.collections.MovieCollectionAdapter;

import java.util.List;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private ItemMovieCollectionBinding binding;
    private MovieCollectionAdapter adapter = new MovieCollectionAdapter();
    private MovieAdapter.MovieCallback movieCallback;
    private MovieCollectionAdapter.MovieClickListener listener;
    public MovieViewHolder(@NonNull ItemMovieCollectionBinding binding, MovieCollectionAdapter.MovieClickListener movieOnClickListener, MovieAdapter.MovieCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        this.movieCallback = callback;
        adapter = new MovieCollectionAdapter(movieOnClickListener);
        initRV();
    }

    public void binding(String collectionTitle, List<Movie> movies) {
        binding.tvCollectionName.setText(collectionTitle);
        adapter.updateAdapter(movies);


    }

    private void initRV() {
        binding.rvMovie.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvMovie.setAdapter(adapter);

    }
}
