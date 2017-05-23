package com.avinash.popularmoviesproject.extras;

import com.avinash.popularmoviesproject.application.MyApplication;

/**
 * Created by AVINASH on 11-04-2017.
 */

public class UrlBuild {
    public static final String URL_POPULAR="https://api.themoviedb.org/3/movie/popular";
    public static final String URL_CHAR_QUESTION="?";
    public static final String URL_CHAR_AMPERSAN="&";
    public static final String URL_PARAMS_API_KEY="?api_key=";
    public static final String URL_PARAMS_LIMIT="&language=en-US&page=";

    public static final String URL_RATED="https://api.themoviedb.org/3/movie/top_rated";

    public static final String URL_MOVIE="https://api.themoviedb.org/3/movie/";
    public static final String URL_REVIEW="/reviews";
    public static final String URL_VIDEO="/videos";


    public static String getRequestURL(int pageLimit){
        return URL_POPULAR+
                URL_PARAMS_API_KEY+ MyApplication.API_KEY
                +URL_PARAMS_LIMIT+pageLimit;
    }

    public static String getRatedURL(int pageLimit){
        return URL_RATED+
                URL_PARAMS_API_KEY+ MyApplication.API_KEY
                +URL_PARAMS_LIMIT+pageLimit;
    }

    public static String getReviewURL(long id,int pageLimit){
        return URL_MOVIE+
                id+
                URL_REVIEW+
                URL_PARAMS_API_KEY+MyApplication.API_KEY+
                URL_PARAMS_LIMIT+pageLimit;
    }

    public static String getVideoUrl(long id, int pageLimit){
        return URL_MOVIE+
                id+
                URL_VIDEO+
                URL_PARAMS_API_KEY+MyApplication.API_KEY+
                URL_PARAMS_LIMIT+pageLimit;
    }
}
