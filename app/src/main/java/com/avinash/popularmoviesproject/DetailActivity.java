package com.avinash.popularmoviesproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.avinash.popularmoviesproject.data.FavContract;
import com.avinash.popularmoviesproject.extras.ReviewJsonUtils;
import com.avinash.popularmoviesproject.extras.TrailerJsonUtils;
import com.avinash.popularmoviesproject.extras.UrlBuild;
import com.avinash.popularmoviesproject.extras.Utils;
import com.avinash.popularmoviesproject.network.VolleySingleton;
import com.avinash.popularmoviesproject.pojo.Movie;
import com.avinash.popularmoviesproject.pojo.Review;
import com.avinash.popularmoviesproject.pojo.Trailer;
import com.avinash.popularmoviesproject.views.OnTrailerClicked;
import com.avinash.popularmoviesproject.views.ReviewAdapter;
import com.avinash.popularmoviesproject.views.TrailerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements OnTrailerClicked, LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    @BindView(R2.id.details_poster)
    ImageView poster;

    @BindView(R2.id.details_date)
    TextView date;
    private java.text.DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @BindView(R2.id.details_synopsis)
    TextView synopsis;

    @BindView(R2.id.details_ratings)
    TextView ratings;

    @BindView(R2.id.details_tv)
    TextView details;

    @BindView(R2.id.details_reviews_error)
    TextView details_reviews_error;

    @BindView(R2.id.details_reviews)
    RecyclerView reviewsRecyclerView;

    @BindView(R2.id.details_trailers_error)
    TextView trailerError;

    @BindView(R2.id.details_trailers)
    RecyclerView trailersRecyclerView;

    @BindView(R2.id.details_iv_fav)
    ImageView ivFav;

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<Review> reviewsList;
    private ReviewAdapter adapter;
    private Movie current;
    private ArrayList<Trailer> trailerList;
    private TrailerAdapter mAdapter;
    private static final int LOADER_ID = 1;
    Boolean isFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        volleySingleton = VolleySingleton.getsInstance(this);
        requestQueue = volleySingleton.getRequestQueue(DetailActivity.this);

        Intent intent = getIntent();
        current = intent.getParcelableExtra("MOVIE_EXTRA");
        details.setText(current.getTitle());
        String formattedDate = dateFormatter.format(current.getReleaseDate());
        date.setText(formattedDate);
        synopsis.setText(current.getSynopsis());
        ratings.setText(" Ratings: "+current.getRating()+"/10");

        Picasso.with(this)
                .load(current.getUrlBackdrop())
                .into(poster);


        setTrailerList();
        setReviewsList();

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
        ivFav.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
    }

    public void setReviewsList(){
        adapter = new ReviewAdapter(this);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsList = new ArrayList<>();
        reviewsRecyclerView.setAdapter(adapter);
        makeVolleyRequest(current.getId());
    }

    public void setTrailerList(){
        mAdapter = new TrailerAdapter(DetailActivity.this,this);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        trailerList = new ArrayList<>();
        trailersRecyclerView.setAdapter(mAdapter);
        makeTrailerVolleyRequest(current.getId());
    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("MOVIE_EXTRA", movie);
        return intent;
    }

    private void makeTrailerVolleyRequest(long id){
        String trailerLink = UrlBuild.getVideoUrl(id,1);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, trailerLink, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                trailerList = TrailerJsonUtils.parseTrailerResponse(response);
                if(trailerList.size()>0){
                    mAdapter.updateData(trailerList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showTrailerError();
            }
        });
        VolleySingleton.getsInstance(this).addToRequestQueue(objectRequest, DetailActivity.this);

    }

    private void makeVolleyRequest(long id){

        String webpage = UrlBuild.getReviewURL(id,1);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, webpage, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                reviewsList = ReviewJsonUtils.parseReviewResponse(response);
                if (reviewsList.isEmpty()){
                    showErrorMessage();
                }else{
                    adapter.updateData(reviewsList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorMessage();

            }
        });
        VolleySingleton.getsInstance(this).addToRequestQueue(request, DetailActivity.this);
    }

    private void showTrailerError(){
        trailersRecyclerView.setVisibility(View.INVISIBLE);
        trailerError.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        reviewsRecyclerView.setVisibility(View.INVISIBLE);
        details_reviews_error.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClicked(Trailer trailer) {
        String key = trailer.getKey();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key)));
    }

    public void addFavs(){
        ContentValues cv = new ContentValues();
        cv.put(FavContract.FavEntry.MOVIE_ID, current.getId());
        cv.put(FavContract.FavEntry.MOVIE_NAME,current.getTitle());
        cv.put(FavContract.FavEntry.MOVIE_DATE,dateFormatter.format(current.getReleaseDate()));
        cv.put(FavContract.FavEntry.MOVIE_URL,current.getUrlImage());
        cv.put(FavContract.FavEntry.MOVIE_POSTER,current.getUrlBackdrop());
        cv.put(FavContract.FavEntry.MOVIE_SYNOPSIS,current.getSynopsis());
        cv.put(FavContract.FavEntry.MOVIE_RATINGS,current.getRating());

        Uri uri = getContentResolver().insert(FavContract.FavEntry.CONTENT_URI, cv);
        if(uri != null){
            Toast.makeText(getBaseContext(), "Added to Favorites",Toast.LENGTH_LONG).show();
        }
    }

    public void deleteFavs(){
        Long id = current.getId();
        String stringId = Long.toString(id);
        Uri uri = FavContract.FavEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        getContentResolver().delete(uri,null,null);
        Toast.makeText(getApplicationContext(),"Removed from Favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mFabData = null;

            @Override
            protected void onStartLoading() {
                if(mFabData!=null){
                    deliverResult(mFabData);
                } else{
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mFabData = data;
                super.deliverResult(data);
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    Long id = current.getId();
                    String stringId = Long.toString(id);
                    Uri uri = FavContract.FavEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(stringId).build();
                    return getContentResolver().query(uri, null, null, null, null);

                } catch (Exception e){
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null && data.getCount()>0){
            Utils.colorise(this, R.color.colorAccent,ivFav);
            isFav = true;
        }
        else {
            Utils.colorise(this,R.color.white,ivFav);
            isFav = false;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        if(isFav){
            deleteFavs();
        } else{
            addFavs();
        }
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
    }
}
