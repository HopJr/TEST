package com.example.themovieapp.views.searchMovie;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapp.databinding.SearchItemBinding;
import com.example.themovieapp.models.entities.Movie;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private List<Movie> mList;
    private MovieClickListener clickListener;

    public SearchAdapter(SearchAdapter.MovieClickListener listener) {
        this.clickListener = listener;

    }

    public SearchAdapter() {
    }

    public void updateAdapter(List<Movie> movieList) {
        this.mList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SearchItemBinding layoutBinding = SearchItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new SearchViewHolder(layoutBinding, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.onBind(mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public interface MovieClickListener {

        void onMovieClick(Movie movie);

    }
}
