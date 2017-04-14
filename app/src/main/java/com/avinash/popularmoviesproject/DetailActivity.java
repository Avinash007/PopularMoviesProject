package com.avinash.popularmoviesproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.avinash.popularmoviesproject.pojo.Movie;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class DetailActivity extends AppCompatActivity {

    private TextView details;
    private ImageView poster;
    private TextView date;
    private java.text.DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private TextView synopsis;
    private TextView ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        details = (TextView) findViewById(R.id.details_tv);
        poster = (ImageView) findViewById(R.id.details_poster);
        date = (TextView) findViewById(R.id.details_date);
        synopsis = (TextView) findViewById(R.id.details_synopsis);
        ratings = (TextView) findViewById(R.id.details_ratings);

        Intent intent = getIntent();
        Movie current = intent.getParcelableExtra("MOVIE_EXTRA");
        details.setText(current.getTitle());
        String formattedDate = dateFormatter.format(current.getReleaseDate());
        date.setText(formattedDate);
        synopsis.setText(current.getSynopsis());
        ratings.setText(" Ratings: "+current.getRating()+"/10");

        Picasso.with(this)
                .load(current.getUrlImage())
                .fit()
                .centerCrop()
                .into(poster);
    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("MOVIE_EXTRA", movie);
        return intent;
    }
}
