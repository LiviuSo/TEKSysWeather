package test.app.teksysweather.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import test.app.teksysweather.R;
import test.app.teksysweather.model.WeatherModel;
import test.app.teksysweather.util.Constants;
import test.app.teksysweather.util.Utilities;

import static test.app.teksysweather.util.Constants.ICON_BASE_URL;

/**
 * Formats and sends data to the views
 */
public class WeatherPresenter {

    private WeatherModel currentWeather;

    public WeatherPresenter() {
    }

    public WeatherModel getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(WeatherModel weatherModel) {
        this.currentWeather = weatherModel;
    }

    public void showIcon(ImageView iconIv) {
        if (currentWeather.weather == null || currentWeather.weather.size() == 0) {
            return;
        }
        String iconURL = String.format("%s%s.png", ICON_BASE_URL, currentWeather.weather.get(0).icon);
        int width = (int) iconIv.getContext().getResources().getDimension(R.dimen.icon_width);
        int height = (int) iconIv.getContext().getResources().getDimension(R.dimen.icon_height);
        Picasso.with(iconIv.getContext())
                .load(iconURL)
                .resize(width, height)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).
                into(iconIv);
    }

    public void showDescription(TextView descrTv) {
        if (currentWeather.weather == null || currentWeather.weather.size() == 0) {
            return;
        }
        descrTv.setText(Utilities.capitalizeFirstLetter(currentWeather.weather.get(0).description));
    }

    public void showLocation(TextView cityTv, TextView countryTv) {
        cityTv.setText(currentWeather.name);
        countryTv.setText(currentWeather.sys.country);
    }

    public void showTime(TextView hourTv, TextView dateTv, TextView dayTv) {
        hourTv.setText(Utilities.getHour(currentWeather.dt * 1000));
        dateTv.setText(Utilities.getDate(currentWeather.dt * 1000));
        dayTv.setText(Utilities.getDayName(dateTv.getContext(), currentWeather.dt * 1000));
    }

    public void showTemperature(TextView tempMinTv, TextView tempTv, TextView tempMaxTv, boolean isMetric) {
        Context context = tempMinTv.getContext();
        tempMinTv.setText(Utilities.formatTemperature(context, currentWeather.main.temp_min, isMetric));
        tempTv.setText(Utilities.formatTemperature(context, currentWeather.main.temp, isMetric));
        tempMaxTv.setText(Utilities.formatTemperature(context, currentWeather.main.temp_max, isMetric));
    }

    public void showRain(TextView rainTv, boolean isMetric) {
        if (currentWeather.rain != null) {
            rainTv.setVisibility(View.VISIBLE);
            Context context = rainTv.getContext();
            String unit = isMetric ? context.getString(R.string.millimeter) : context.getString(R.string.inch);
            double quantity = isMetric ? currentWeather.rain.threeHours : 0.0393701 * currentWeather.rain.threeHours;
            rainTv.setText(String.format(Locale.getDefault(), "%.1f %s", quantity, unit));

        } else {
            rainTv.setVisibility(View.GONE);
        }
    }

    public void showSnow(TextView snowTv, boolean isMetric) {
        if (currentWeather.snow != null) {
            snowTv.setVisibility(View.VISIBLE);
            Context context = snowTv.getContext();
            String unit = isMetric ? context.getString(R.string.millimeter) : context.getString(R.string.inch);
            double quantity = isMetric ? currentWeather.rain.threeHours : 0.0393701 * currentWeather.rain.threeHours;
            snowTv.setText(String.format(Locale.getDefault(), "%.1f %s", quantity, unit));
        } else {
            snowTv.setVisibility(View.GONE);
        }
    }

    public void showWind(TextView windTv, boolean isWindShown) {
        if (isWindShown && currentWeather.wind != null) {
            windTv.setVisibility(View.VISIBLE);
            windTv.setText(Utilities.getFormattedWind(windTv.getContext(), currentWeather.wind.speed, currentWeather.wind.deg));
        } else {
            windTv.setVisibility(View.GONE);
        }
    }

    public void showClouds(TextView cloudsTv, boolean isCloudsShown) {
        if (isCloudsShown && currentWeather.clouds != null) {
            cloudsTv.setVisibility(View.VISIBLE);
            cloudsTv.setText(String.format(Locale.getDefault(), "%d%%", currentWeather.clouds.all));
        } else {
            cloudsTv.setVisibility(View.GONE);
        }
    }

    public void showPressure(TextView pressureTv, boolean isPressureShown) {
        if (isPressureShown && currentWeather != null && currentWeather.main != null) {
            pressureTv.setVisibility(View.VISIBLE);
            pressureTv.setText(String.format(Locale.getDefault(), "%d hPa", currentWeather.main.pressure));
        } else {
            pressureTv.setVisibility(View.GONE);
        }
    }

    public void showHumidity(TextView humidityTv, boolean isHumidityShown) {
        if (isHumidityShown && currentWeather.main != null) {
            humidityTv.setVisibility(View.VISIBLE);
            humidityTv.setText(String.format(Locale.getDefault(), "%d%%", currentWeather.main.humidity));
        } else {
            humidityTv.setVisibility(View.GONE);
        }
    }

    public void showSunrise(TextView sunriseTv, boolean isSunriseShown) {
        if (isSunriseShown && currentWeather.sys != null) {
            sunriseTv.setVisibility(View.VISIBLE);
            sunriseTv.setText(Utilities.getHour(currentWeather.sys.sunrise * 1000));
        } else {
            sunriseTv.setVisibility(View.GONE);
        }
    }

    public void showSunset(TextView sunsetTv, boolean isSunsetShown) {
        if (isSunsetShown && currentWeather.sys != null) {
            sunsetTv.setVisibility(View.VISIBLE);
            sunsetTv.setText(Utilities.getHour(currentWeather.sys.sunset * 1000));
        } else {
            sunsetTv.setVisibility(View.GONE);
        }
    }

    public void saveModelToSharePref(Activity context) {
        // 'serialize' the model
        Gson gson = new Gson();
        String modelAsString = gson.toJson(currentWeather);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.SHARED_PREF_WEATHER_MODEL, modelAsString);
        editor.apply();
    }

    public WeatherModel loadModelFromSharedPref(Activity context) {
        // 'deserialize' the model
        Gson gson = new Gson();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String currentWeatherAsString = sharedPref.getString(Constants.SHARED_PREF_WEATHER_MODEL, "");
        if (!currentWeatherAsString.equals("")) {
            currentWeather = gson.fromJson(currentWeatherAsString, WeatherModel.class);
        }
        return currentWeather;
    }
}