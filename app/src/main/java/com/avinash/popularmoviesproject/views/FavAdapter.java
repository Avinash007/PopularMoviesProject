package com.avinash.popularmoviesproject.views;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avinash.popularmoviesproject.R;
import com.avinash.popularmoviesproject.R2;
import com.avinash.popularmoviesproject.data.FavContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AVINASH on 21-05-2017.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public FavAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_movie_fav, parent, false);
        FavViewHolder holder = new FavViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(FavContract.FavEntry.MOVIE_ID);
        int nameIndex = mCursor.getColumnIndex(FavContract.FavEntry.MOVIE_NAME);
        int dateIndex = mCursor.getColumnIndex(FavContract.FavEntry.MOVIE_DATE);
        int ratingsIndex = mCursor.getColumnIndex(FavContract.FavEntry.MOVIE_RATINGS);
        int posterIndex = mCursor.getColumnIndex(FavContract.FavEntry.MOVIE_URL);
        int synopsisIndex = mCursor.getColumnIndex(FavContract.FavEntry.MOVIE_SYNOPSIS);

        mCursor.moveToPosition(position);

        String name = mCursor.getString(nameIndex);
        String date = mCursor.getString(dateIndex);
        String ratings = mCursor.getString(ratingsIndex);
        String posterUrl = mCursor.getString(posterIndex);
        String synopsis = mCursor.getString(synopsisIndex);

        holder.populate(mContext,posterUrl,name,date,ratings,synopsis);

    }

    @Override
    public int getItemCount() {
        if(mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c){
        if(mCursor == c){   //Check if they are same
            return null;
        }

        Cursor temp = mCursor;  //Store old value in temp
        this.mCursor =c;        //Store new value in mCursor
        if(c != null){          //If new value is valid cursor then notify data set Changed
            notifyDataSetChanged();
        }
        return temp;            //or return temp
    }

    class FavViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.fav_image)
        ImageView poster;

        @BindView(R2.id.fav_title)
        TextView titleView;

        @BindView(R2.id.fav_date)
        TextView dateView;

        @BindView(R2.id.fav_ratings)
        TextView ratingsView;

        @BindView(R2.id.fav_synopsis)
        TextView synopsisView;

        public FavViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populate(Context context, String posterPath, String title, String date, String ratings, String synopsis){
            Picasso.with(context)
                    .load(posterPath)
                    .centerCrop()
                    .fit()
                    .into(poster);
            titleView.setText(title);
            dateView.setText(date);
            ratingsView.setText(ratings);
            synopsisView.setText(synopsis);

        }


    }
}
