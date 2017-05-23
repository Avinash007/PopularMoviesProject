package com.avinash.popularmoviesproject.application;

import android.app.Application;

/**
 * Created by AVINASH on 11-04-2017.
 */

public class MyApplication extends Application {
    //TODO add the Movie Database API Key here
    public static final String API_KEY ="";

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }
}
