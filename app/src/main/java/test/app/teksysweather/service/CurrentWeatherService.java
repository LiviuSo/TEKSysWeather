package test.app.teksysweather.service;

/**
 * Created by lsoco_user on 1/7/2017.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.app.teksysweather.model.WeatherModel;

import static test.app.teksysweather.util.Constants.APP_ID;


/**
 * Retrofit service to fetch the current weather for a specified city
 * It returns the temperature as metric (Celsius degrees)
 */
public interface CurrentWeatherService {

    @GET("/data/2.5/weather?appid=" + APP_ID)
    Call<WeatherModel> fetchCurrentWeather(@Query("q") String city, @Query("units") String units);
}