package test.app.teksysweather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import test.app.teksysweather.model.WeatherModel;

/**
 * Class holding String, and time/date format utilities
 */
public class Utilities {

    /**
     * Tests whether a string is null or empty
     * @param string The string
     * @return Wheather null or empty
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
