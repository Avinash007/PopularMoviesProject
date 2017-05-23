package com.avinash.popularmoviesproject;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.avinash.popularmoviesproject.data.FavContract;
import com.avinash.popularmoviesproject.views.FavAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG="User Log";
    private static final int FAV_LOADER_ID=0;

    private FavAdapter mAdapter;

    @BindView(R2.id.fav_rv)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        ButterKnife.bind(this);

        mAdapter = new FavAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(FAV_LOADER_ID,null,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(FAV_LOADER_ID,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mFavData = null;

            @Override
            protected void onStartLoading() {
                if(mFavData !=null){
                    deliverResult(mFavData);
                } else{
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().
                            query(FavContract.FavEntry.CONTENT_URI, null, null, null, FavContract.FavEntry.MOVIE_NAME);
                }
                catch (Exception e){
                    Log.i(TAG,"Failed to asynchronously load data");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data){
                mFavData = data;
                super.deliverResult(data);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
