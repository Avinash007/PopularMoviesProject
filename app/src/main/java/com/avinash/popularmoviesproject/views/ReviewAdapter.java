package com.avinash.popularmoviesproject.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avinash.popularmoviesproject.R;
import com.avinash.popularmoviesproject.pojo.Review;

import java.util.ArrayList;

/**
 * Created by AVINASH on 16-05-2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    ArrayList<Review> reviewArray;
    LayoutInflater inflater;
    Context context;

    public ReviewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void updateData(ArrayList<Review> listReview){
        reviewArray = listReview;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.list_movie_review, parent, false);
        ReviewViewHolder holder = new ReviewViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review current = reviewArray.get(position);
        holder.populate(context,current);

    }

    @Override
    public int getItemCount() {
        if(reviewArray == null || reviewArray.isEmpty()) {
            return 0;
        }
        else
            return reviewArray.size();
    }
}
