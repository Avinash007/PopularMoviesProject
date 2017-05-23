package com.avinash.popularmoviesproject.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avinash.popularmoviesproject.R2;
import com.avinash.popularmoviesproject.pojo.Trailer;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AVINASH on 17-05-2017.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R2.id.trailer_poster)
    ImageView poster;

    @BindView(R2.id.trailer_name)
    TextView name;

    @BindView(R2.id.trailer_type)
    TextView type;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(Context context, Trailer trailer){
        itemView.setTag(trailer);
        name.setText(trailer.getName());
        type.setText(trailer.getType());
        String url = "http://img.youtube.com/vi/"+ trailer.getKey()+"/default.jpg";
        Picasso.with(context)
                .load(url)
                .fit()
                .into(poster);

    }


}
