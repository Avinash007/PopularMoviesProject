package com.avinash.popularmoviesproject.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.avinash.popularmoviesproject.R2;
import com.avinash.popularmoviesproject.pojo.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AVINASH on 12-04-2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R2.id.list_movie_poster_iv)
    ImageView poster;

    @BindView(R2.id.list_movie_poster_pb)
    ProgressBar progressBar;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
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
