package test.app.teksysweather.service;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import test.app.teksysweather.model.WeatherModel;

import static test.app.teksysweather.util.Constants.ICON_BASE_URL;

/**
 * Formats and sends data to the views
 */
public class WeatherPresenter {

    private WeatherModel currentWeather;

    public WeatherPresenter(WeatherModel weatherModel) {
        this.currentWeather = weatherModel;
    }

    public WeatherModel getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(WeatherModel weatherModel) {
        this.currentWeather = weatherModel;
    }

    public void showIcon(ImageView iconIv) {
        if(currentWeather.weather == null || currentWeather.weather.size() == 0) {
            return;
        }
        String iconURL = String.format("%s%s.png", ICON_BASE_URL, currentWeather.weather.get(0).icon);
        Picasso.with(iconIv.getContext()).load(iconURL).resize(100, 100).into(iconIv);
    }

    public void showDescription(TextView descrTv) {
        if(currentWeather.weather == null || currentWeather.weather.size() == 0) {
            return;
        }
        descrTv.setText(String.format(Locale.getDefault(), "%s", currentWeather.weather.get(0).description));
    }

    public void showLocation(TextView cityTv, TextView countryTv) {
        cityTv.setText(currentWeather.name);
        countryTv.setText(currentWeather.sys.country);
    }

    public void showTime(TextView hourTv, TextView dateTv) {
        hourTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.dt)); // TODO: 1/7/2017 util to extract time/date
        dateTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.dt));
    }

    public void showTemperature(TextView tempMinTv, TextView tempTv, TextView tempMaxTv) {
        tempMinTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.main.temp_min));
        tempTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.main.temp));
        tempMaxTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.main.temp_max));
    }

    public void showRain(TextView rainTv) {
        if (currentWeather.rain != null) {
            rainTv.setVisibility(View.VISIBLE);
            rainTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.rain.threeHours));
        } else {
            rainTv.setVisibility(View.GONE);
        }
    }

    public void showSnow(TextView snowTv) {
        if (currentWeather.snow != null) {
            snowTv.setVisibility(View.VISIBLE);
            snowTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.snow.threeHours));
        } else {
            snowTv.setVisibility(View.GONE);
        }
    }

    public void showWind(TextView windTv, boolean isWindShown) {
        if (isWindShown && currentWeather.wind != null) {
            windTv.setVisibility(View.VISIBLE);
            windTv.setText(String.format(Locale.getDefault(), "%.2f %d", currentWeather.wind.speed, currentWeather.wind.deg));
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
            pressureTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.main.pressure));
        } else {
            pressureTv.setVisibility(View.GONE);
        }
    }

    public void showHumidity(TextView humidityTv, boolean isHumidityShown) {
        if (isHumidityShown && currentWeather.main != null) {
            humidityTv.setVisibility(View.VISIBLE);
            humidityTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.main.humidity));
        } else {
            humidityTv.setVisibility(View.GONE);
        }
    }

    public void showSunrise(TextView sunriseTv, boolean isSunriseShown) {
        if (isSunriseShown && currentWeather.sys != null) {
            sunriseTv.setVisibility(View.VISIBLE);
            sunriseTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.sys.sunrise));
        } else {
            sunriseTv.setVisibility(View.GONE);
        }
    }

    public void showSunset(TextView sunsetTv, boolean isSunsetShown) {
        if (isSunsetShown && currentWeather.sys != null) {
            sunsetTv.setVisibility(View.VISIBLE);
            sunsetTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.sys.sunset));
        } else {
            sunsetTv.setVisibility(View.GONE);
        }
    }
}