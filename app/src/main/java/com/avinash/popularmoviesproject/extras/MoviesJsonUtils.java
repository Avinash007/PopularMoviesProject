package com.avinash.popularmoviesproject.extras;

import com.avinash.popularmoviesproject.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_BACKDROP;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_ID;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_MOVIES;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_POSTER;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_RATINGS;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_RELEASE_DATES;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_SYNOPSIS;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_TITLE;

/**
 * Created by AVINASH on 12-04-2017.
 */

public class MoviesJsonUtils {


    public static ArrayList<Movie> parseMovieResponse(JSONObject response){
        ArrayList<Movie> moviesList = new ArrayList<>();

        try {
            if(response!= null || response.length() >0){
                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);

                for(int i=0; i<arrayMovies.length(); i++){
                    long id = -1;
                    String title = Constants.NA;
                    String releaseDate = Constants.NA;
                    Double ratings = -1.0;
                    String synopsis = Constants.NA;
                    String posterPath = Constants.NA;
                    String backdropPath = Constants.NA;

                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    if(currentMovie.has(KEY_ID) && !currentMovie.isNull(KEY_ID)){
                        id = currentMovie.getLong(KEY_ID);
                    }
                    if(currentMovie.has(KEY_TITLE) && !currentMovie.isNull(KEY_TITLE)){
                        title = currentMovie.getString(KEY_TITLE);
                    }
                    if(currentMovie.has(KEY_RELEASE_DATES) && !currentMovie.isNull(KEY_RELEASE_DATES)){
                        releaseDate = currentMovie.getString(KEY_RELEASE_DATES);
                    }
                    if(currentMovie.has(KEY_RATINGS) && !currentMovie.isNull(KEY_RATINGS)){
                        ratings = currentMovie.getDouble(KEY_RATINGS);
                    }
                    if(currentMovie.has(KEY_SYNOPSIS) && !currentMovie.isNull(KEY_SYNOPSIS)){
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }
                    if(currentMovie.has(KEY_POSTER) && !currentMovie.isNull(KEY_POSTER)){
                        posterPath = "https://image.tmdb.org/t/p/w500" + currentMovie.getString(KEY_POSTER);
                    }
                    if(currentMovie.has(KEY_BACKDROP) && !currentMovie.isNull(KEY_BACKDROP)){
                        backdropPath = "https://image.tmdb.org/t/p/w500" + currentMovie.getString(KEY_BACKDROP);
                    }


                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = null;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date = dateFormat.parse(releaseDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    movie.setReleaseDate(date);
                    movie.setRating(ratings);
                    movie.setSynopsis(synopsis);
                    movie.setUrlImage(posterPath);
                    movie.setUrlBackdrop(backdropPath);

                    moviesList.add(movie);
                }
                return moviesList;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
