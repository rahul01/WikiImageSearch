package com.rhl.imagewiki;

import android.content.Context;
import android.net.ConnectivityManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton network manager provides network related utilities for application
 */
public class NetworkManager {

    private static NetworkManager mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    /**
     * wikipedia image search base url
     */
    public static final String BASE_URL = "https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=500&pilimit=50&generator=prefixsearch&gpssearch=";

    //private constructor
    private NetworkManager(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * provides Network manager instance
     *
     * @param context context
     * @return instance of NetworkManager
     */
    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() prevents leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * adds request to request queue
     *
     * @param req request object
     * @param <T> type of request object
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * cancels all pending requests
     */
    public void cancelPending() {
        getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    /**
     * checks whether network is available or not
     *
     * @return whether network is available or not
     */
    public boolean isOnline() {

        if (mContext == null) {
            return false;
        }
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }
}