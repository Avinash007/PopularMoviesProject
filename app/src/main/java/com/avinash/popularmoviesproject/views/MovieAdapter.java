package com.avinash.popularmoviesproject.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avinash.popularmoviesproject.R;
import com.avinash.popularmoviesproject.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by AVINASH on 12-04-2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> implements View.OnClickListener {

    ArrayList<Movie> moviesArray;
    private LayoutInflater inflater;
    Context mContext;
    private OnMovieClicked listener;

    public MovieAdapter(Context context, OnMovieClicked mListener) {
        mContext = context;
        listener = mListener;
        inflater = LayoutInflater.from(context);
    }

    public void updateData(ArrayList<Movie> listMovies){
        moviesArray = listMovies;
        notifyDataSetChanged();
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.list_movie_poster, parent, false);
        MovieViewHolder holder = new MovieViewHolder(rootView);
        rootView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie current = moviesArray.get(position);
        holder.populate(mContext,current);

    }

    @Override
    public int getItemCount() {
        if(moviesArray == null || moviesArray.isEmpty()){
            return 0;
        }
        else{
            return moviesArray.size();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getTag() instanceof Movie){
            Movie movie = (Movie) view.getTag();
            listener.movieClicked(movie);
        }
    }
}
