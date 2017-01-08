package test.app.teksysweather.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

/**
 * Class holding String, and time/date format utilities
 */
public class Utilities {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean hasSavedSharedPref(Activity context, String key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String content = sharedPref.getString(key, "");

        return !content.equals("");
    }
}