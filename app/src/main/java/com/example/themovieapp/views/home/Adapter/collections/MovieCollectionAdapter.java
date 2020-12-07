package com.example.themovieapp.views.home.Adapter.collections;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapp.databinding.MovieCardBinding;
import com.example.themovieapp.models.entities.Movie;

import java.util.List;

public class MovieCollectionAdapter extends RecyclerView.Adapter<MovieCollectionViewHolder> {
    private List<Movie> movieList;
    private MovieClickListener onClickListener;

    public MovieCollectionAdapter() {
        notifyDataSetChanged();
    }

    public MovieCollectionAdapter(MovieClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void updateAdapter(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieCardBinding view = MovieCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieCollectionViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCollectionViewHolder holder, int position) {
        holder.binding(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public interface MovieClickListener {

        void onMovieClick(Movie movie);

    }
}
