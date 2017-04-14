package com.avinash.popularmoviesproject;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.avinash.popularmoviesproject.extras.MoviesJsonUtils;
import com.avinash.popularmoviesproject.extras.NetworkUtils;
import com.avinash.popularmoviesproject.extras.UrlBuild;
import com.avinash.popularmoviesproject.pojo.Movie;
import com.avinash.popularmoviesproject.views.MovieAdapter;
import com.avinash.popularmoviesproject.views.OnMovieClicked;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements OnMovieClicked {

    private TextView movies;
    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;
    public static int POPULAR=1;
    public static int RATED=2;
    private ArrayList<Movie> movieList;
    public static final String MOVIES_ARRAY="MOVIES_ARRAY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movieList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.movies_rv);
        movies = (TextView) findViewById(R.id.tv_movies);

        mAdapter = new MovieAdapter(MoviesActivity.this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(mAdapter);
        if(savedInstanceState!=null){
            movieList = savedInstanceState.getParcelableArrayList(MOVIES_ARRAY);
            if(!movieList.isEmpty()) {
                mAdapter.updateData(movieList);
                showDataMessage();
            }
            else{
                showErrorMessage();
            }
        }
        else{
            makeMovieSearchQuery(POPULAR);
        }

    }


    public void makeMovieSearchQuery(int filterOptions){
        String webpage = UrlBuild.getRequestURL(1);
        if(filterOptions == RATED) {
            webpage = UrlBuild.getRatedURL(1);
            Toast.makeText(getApplicationContext(),"Loading Top Rated movies... ",Toast.LENGTH_LONG).show();
        }
        new MovieQueryTask().execute(webpage);
        showDataMessage();
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

    public class MovieQueryTask extends AsyncTask<String, Void, ArrayList<Movie>> {



        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            String movieText = null;
            ArrayList<Movie> movieArrayList = new ArrayList<>();
            String webpage = strings[0];
            URL url = null;
            try {
                url = new URL(webpage);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                movieText = NetworkUtils.getResponseFromHTTPURL(url);
                movieArrayList = MoviesJsonUtils.parseMovieResponse(movieText);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> s) {
            if(!s.isEmpty()){
                showDataMessage();
                mAdapter.updateData(s);
                movieList =s;

            }
            else{
                showErrorMessage();
            }
        }
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
                    break;
            case R.id.action_popular:
                filterOptions = POPULAR;
                    break;
            default:
                handled = false;
        }
        makeMovieSearchQuery(filterOptions);

        return handled;
    }
}

