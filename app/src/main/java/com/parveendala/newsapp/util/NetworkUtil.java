package com.parveendala.newsapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
public class NetworkUtil {

    private ConnectivityManager connectivityManager;

    public NetworkUtil(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }
}
