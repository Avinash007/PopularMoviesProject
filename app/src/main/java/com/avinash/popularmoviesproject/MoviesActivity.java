package com.avinash.popularmoviesproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.avinash.popularmoviesproject.extras.MoviesJsonUtils;
import com.avinash.popularmoviesproject.extras.UrlBuild;
import com.avinash.popularmoviesproject.network.VolleySingleton;
import com.avinash.popularmoviesproject.pojo.Movie;
import com.avinash.popularmoviesproject.views.MovieAdapter;
import com.avinash.popularmoviesproject.views.OnMovieClicked;

import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements OnMovieClicked {

    private TextView movies;
    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;
    public static int POPULAR=1;
    public static int RATED=2;
    private ArrayList<Movie> movieList;
    public static final String MOVIES_ARRAY="MOVIES_ARRAY";
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movieList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.movies_rv);
        movies = (TextView) findViewById(R.id.tv_movies);
        volleySingleton = VolleySingleton.getsInstance(this);
        requestQueue = volleySingleton.getRequestQueue(MoviesActivity.this);

        mAdapter = new MovieAdapter(MoviesActivity.this, this);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }

        recyclerView.setAdapter(mAdapter);
        if(savedInstanceState!=null){
            movieList = savedInstanceState.getParcelableArrayList(MOVIES_ARRAY);
            if(!movieList.isEmpty()) {
                mAdapter.updateData(movieList);
                showDataMessage();
            }
            else{
                makeVolleyRequest(POPULAR);
            }
        }
        else{
            makeVolleyRequest(POPULAR);
        }

    }

    public void makeVolleyRequest(int filterOptions){
        String webpage = null;
        if(filterOptions == RATED){
            webpage = UrlBuild.getRatedURL(1);
            Toast.makeText(getApplicationContext(),"Loading Top Rated Movies",Toast.LENGTH_SHORT).show();
        } else{
            webpage = UrlBuild.getRequestURL(1);
            Toast.makeText(getApplicationContext(),"Loading Popular Movies",Toast.LENGTH_SHORT).show();
        }
        showDataMessage();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, webpage,null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                movieList = parseJsonResponse(response);
                mAdapter.updateData(movieList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorMessage();
            }
        });
        requestQueue.add(request);
    }

    public ArrayList<Movie> parseJsonResponse(JSONObject response){
        ArrayList<Movie> s = MoviesJsonUtils.parseMovieResponse(response);
        return s;
    }

    private void showDataMessage(){
        movies.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        Log.i("Data","S got some value");
    }

    private void showErrorMessage(){
        movies.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        Log.i("Data","No response from s");
    }

    @Override
    public void movieClicked(Movie movie) {
        Intent intent = DetailActivity.newIntent(MoviesActivity.this,movie);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES_ARRAY,movieList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;
        int filterOptions=0;
        int id = item.getItemId();
        switch(id){
            case  R.id.action_rated:
                filterOptions = RATED;
                makeVolleyRequest(filterOptions);
                    break;
            case R.id.action_popular:
                filterOptions = POPULAR;
                makeVolleyRequest(filterOptions);
                    break;
            case R.id.action_favorite:
                startActivity(new Intent(MoviesActivity.this,FavActivity.class));
            default:
                handled = false;
        }


        return handled;
    }
}

