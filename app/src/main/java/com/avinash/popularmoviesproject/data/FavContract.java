package com.avinash.popularmoviesproject.data;

import android.net.Uri;

/**
 * Created by AVINASH on 19-05-2017.
 */

public class FavContract {

    public static final String AUTHORITY="com.avinash.popularmoviesproject";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_FAV="fav";

    public static final class FavEntry {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();

        //Table name
        public static final String TABLE_NAME="fav";

        //Table column name in String
        public static final String MOVIE_NAME="title";
        public static final String MOVIE_ID="id";
        public static final String MOVIE_DATE="date";
        public static final String MOVIE_SYNOPSIS="synopsis";
        public static final String MOVIE_RATINGS="ratings";
        public static final String MOVIE_URL="url";
        public static final String MOVIE_POSTER="poster";


    }
}
