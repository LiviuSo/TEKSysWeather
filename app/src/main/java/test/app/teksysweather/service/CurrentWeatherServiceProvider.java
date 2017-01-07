package test.app.teksysweather.service;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Singleton that provides a retrofit service
 */
public class CurrentWeatherServiceProvider {

    private static CurrentWeatherServiceProvider instance;
    private        Retrofit                      retrofit;
    private        String                        baseURL;

    private CurrentWeatherServiceProvider(String baseURL) {
        this.baseURL = baseURL;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public static CurrentWeatherServiceProvider getInstance(String baseURL) {
        if (instance == null || !instance.baseURL.equals(baseURL)) {
            instance = new CurrentWeatherServiceProvider(baseURL);
        }
        return instance;
    }

    public CurrentWeatherService getCurrentWeatherService() {
        return retrofit.create(CurrentWeatherService.class);
    }
}
