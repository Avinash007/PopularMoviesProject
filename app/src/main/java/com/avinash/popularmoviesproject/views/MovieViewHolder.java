package com.avinash.popularmoviesproject.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.avinash.popularmoviesproject.R;
import com.avinash.popularmoviesproject.pojo.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by AVINASH on 12-04-2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    ImageView poster;
    ProgressBar progressBar;

    public MovieViewHolder(View itemView) {
        super(itemView);
        poster = (ImageView) itemView.findViewById(R.id.list_movie_poster_iv);
        progressBar = (ProgressBar) itemView.findViewById(R.id.list_movie_poster_pb);
    }

    public void populate(Context context, Movie movie){
        itemView.setTag(movie);
        Picasso.with(context)
                .load(movie.getUrlImage())
                .fit()
                .centerCrop()
                .into(poster, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

    }
}
