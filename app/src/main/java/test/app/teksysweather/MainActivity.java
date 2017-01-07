package test.app.teksysweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.app.teksysweather.model.WeatherModel;

public class MainActivity extends AppCompatActivity {

    public static final  String BASE_URL = "http://api.openweathermap.org";
    public static final  String APP_ID   = "78fc098959a0feb86ab7ca2faa0da499";
    private static final String LOG_TAG  = MainActivity.class.getSimpleName();

    private WeatherModel currentWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<WeatherModel> weatherCall = CurrentWeatherServiceProvider.getInstance(BASE_URL)
                .getCurrentWeatherService()
                .fetchCurrentWeather("Montreal");
        weatherCall.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                currentWeather = response.body();
                Log.v(LOG_TAG, "retrofit success!");
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.d(LOG_TAG, "retrofit onFailure()", t);
            }
        });
    }

    /**
     * Singleton that provides a retrofit service
     */
    static class CurrentWeatherServiceProvider {

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

        static CurrentWeatherServiceProvider getInstance(String baseURL) {
            if (instance == null || !instance.baseURL.equals(baseURL)) {
                instance = new CurrentWeatherServiceProvider(baseURL);
            }
            return instance;
        }

        CurrentWeatherService getCurrentWeatherService() {
            return retrofit.create(CurrentWeatherService.class);
        }
    }

    /**
     * Retrofit service to fetch the current weather for a specified city
     * It returns the temperature as metric (Celsius degrees)
     */
    interface CurrentWeatherService {

        @GET("/data/2.5/weather?units=metric&appid=" + APP_ID)
        Call<WeatherModel> fetchCurrentWeather(@Query("q") String city);
    }
}