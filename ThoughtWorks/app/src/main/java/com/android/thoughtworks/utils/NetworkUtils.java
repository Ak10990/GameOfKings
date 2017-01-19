package com.android.thoughtworks.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static boolean isInternetConnected = false;

    public static final boolean isInternetConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isInternetConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return isInternetConnected;
    }
}