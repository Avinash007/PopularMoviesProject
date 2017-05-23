package com.avinash.popularmoviesproject.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.avinash.popularmoviesproject.R2;
import com.avinash.popularmoviesproject.pojo.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AVINASH on 16-05-2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R2.id.review_name)
    TextView name;

    @BindView(R2.id.review_text)
    TextView userReview;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void populate(Context context, Review review){
        name.setText(review.getAuthor());
        userReview.setText(review.getContent());
    }
}
