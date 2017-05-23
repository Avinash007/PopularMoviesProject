package com.avinash.popularmoviesproject.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avinash.popularmoviesproject.R;
import com.avinash.popularmoviesproject.pojo.Trailer;

import java.util.ArrayList;

/**
 * Created by AVINASH on 17-05-2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> implements View.OnClickListener {
    ArrayList<Trailer> trailers;
    LayoutInflater inflater;
    Context context;
    OnTrailerClicked clickListener;

    public TrailerAdapter(Context context, OnTrailerClicked clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        inflater = LayoutInflater.from(context);
    }

    public void updateData(ArrayList<Trailer> trailerArrayList){
        trailers = trailerArrayList;
        notifyDataSetChanged();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.list_movie_trailer,parent,false);
        TrailerViewHolder holder = new TrailerViewHolder(rootView);
        rootView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Trailer current = trailers.get(position);
        holder.populate(context,current);

    }

    @Override
    public int getItemCount() {
        if(trailers == null || trailers.isEmpty()) {
            return 0;
        }
        else{
            return trailers.size();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getTag() instanceof Trailer){
            Trailer trailer = (Trailer) view.getTag();
            clickListener.onClicked(trailer);
        }
    }
}
