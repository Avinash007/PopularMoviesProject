package com.avinash.popularmoviesproject.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by AVINASH on 19-05-2017.
 */

public class FavContentProvider extends ContentProvider {

    private FavDbHelper dbHelper;
    public static final int FAVS=100;
    public static final int FAVS_WITH_ID=101;
    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //For directory
        uriMatcher.addURI(FavContract.AUTHORITY,FavContract.PATH_FAV,FAVS);

        //For single element... read and delete
        uriMatcher.addURI(FavContract.AUTHORITY,FavContract.PATH_FAV +"/#",FAVS_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new FavDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case FAVS:
                //Insert movies in database
                long id = db.insert(FavContract.FavEntry.TABLE_NAME,null,contentValues);
                if(id>0){   //Insert successful and we get an id
                    returnUri = ContentUris.withAppendedId(FavContract.FavEntry.CONTENT_URI,id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into "+ uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }

        return returnUri;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor retCursor;
        int match = sUriMatcher.match(uri);
        switch (match){
            case FAVS:
                retCursor = db.query(FavContract.FavEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String[] mSelectionArgs = new String[]{id};
                String mSelection = FavContract.FavEntry.MOVIE_ID +"=?";
                retCursor = db.query(FavContract.FavEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Not supported uri" +uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int movieDeleted;
        switch (match){
            case FAVS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String[] mSelectionArgs = new String[]{id};
                movieDeleted = db.delete(FavContract.FavEntry.TABLE_NAME, FavContract.FavEntry.MOVIE_ID+"=?", mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri "+uri);
        }
        if(movieDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
