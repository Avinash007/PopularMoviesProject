package com.avinash.popularmoviesproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AVINASH on 19-05-2017.
 */

public class FavDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="favDb.db";
    private static final int VERSION=1;

    public FavDbHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "+ FavContract.FavEntry.TABLE_NAME+ " ("+
                FavContract.FavEntry.MOVIE_ID + " INTEGER PRIMARY KEY, "+
                FavContract.FavEntry.MOVIE_NAME+ " STRING NOT NULL, "+
                FavContract.FavEntry.MOVIE_DATE+ " STRING NOT NULL, "+
                FavContract.FavEntry.MOVIE_POSTER+ " STRING NOT NULL, "+
                FavContract.FavEntry.MOVIE_SYNOPSIS+ " STRING NOT NULL, "+
                FavContract.FavEntry.MOVIE_URL+ " STRING NOT NULL, "+
                FavContract.FavEntry.MOVIE_RATINGS+ " FLOAT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + FavContract.FavEntry.TABLE_NAME);
        onCreate(db);
    }
}
