package test.app.teksysweather.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Locale;

import test.app.teksysweather.R;

/**
 * Class holding utilities to format Strings and time/date
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

    public static boolean isMetric(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_unit_key),
                               context.getString(R.string.pref_unit_metric))
                .equals(context.getString(R.string.pref_unit_metric));
    }

    public static String formatTemperature(Context context, double temperature, boolean isMetric) {
        double temp;
        if (!isMetric) {
            temp = 9 * temperature / 5 + 32;
        } else {
            temp = temperature;
        }
        String tempString;
        if (isMetric) {
            tempString = context.getString(R.string.format_temperature, temp);
        } else {
            tempString = String.format(Locale.getDefault(), "%1.0f", temp);
        }
        return tempString;
    }

    public static String getFormattedWind(Context context, double windSpeed, float degrees) {
        int windFormat;
        if (Utilities.isMetric(context)) {
            windFormat = R.string.format_wind_kmh;
        } else {
            windFormat = R.string.format_wind_mph;
            windSpeed = .621371192237334f * windSpeed;
        }

        // From wind direction in degrees, determine compass direction as a string (e.g NW)
        // You know what's fun, writing really long if/else statements with tons of possible
        // conditions.  Seriously, try it!
        String direction = "Unknown";
        if (degrees >= 337.5 || degrees < 22.5) {
            direction = "N";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            direction = "NE";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            direction = "E";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            direction = "SE";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            direction = "S";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            direction = "SW";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            direction = "W";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            direction = "NW";
        }
        return String.format(context.getString(windFormat), windSpeed, direction);
    }

    public static String getHour(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        return String.format(Locale.getDefault(), "%d:%d",
                             calendar.get(Calendar.HOUR),
                             calendar.get(Calendar.MINUTE));
    }

    public static String getHour24(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        return String.format(Locale.getDefault(), "%02d:%02d:%02d",
                             calendar.get(Calendar.HOUR_OF_DAY),
                             calendar.get(Calendar.MINUTE),
                             calendar.get(Calendar.SECOND));
    }

    public static String getDate(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        return String.format(Locale.getDefault(), "%d/%d/%d",
                             calendar.get(Calendar.MONTH) + 1,
                             calendar.get(Calendar.DAY_OF_MONTH),
                             calendar.get(Calendar.YEAR));
    }

    public static String getAmPm(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        String[] amPm = {"AM", "PM"};
        return amPm[calendar.get(Calendar.AM_PM)];
    }

    public static String getDayName(Context context, long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        String[] weekdays = context.getResources().getStringArray(R.array.weekdays);
        return weekdays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static String capitalizeFirstLetter(String string) {
        return String.format(Locale.getDefault(), "%c%s", Character.toUpperCase(string.charAt(0)), string.substring(1));
    }
}