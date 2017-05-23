package com.avinash.popularmoviesproject.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by AVINASH on 15-04-2017.
 */

public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;


    public VolleySingleton(Context context) {
        mRequestQueue = getRequestQueue(context);
    }

    public RequestQueue getRequestQueue(Context context){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public static VolleySingleton getsInstance(Context context){
        if(sInstance == null){
            sInstance = new VolleySingleton(context);
        }
        return sInstance;
    }

    public <T> void addToRequestQueue(Request<T> req, Context context){
        req.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue(context).add(req);
    }
}
