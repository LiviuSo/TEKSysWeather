package test.app.teksysweather.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.app.teksysweather.R;
import test.app.teksysweather.model.WeatherModel;

public class MainActivity extends AppCompatActivity {

    public static final  String BASE_URL = "http://api.openweathermap.org";
    public static final  String APP_ID   = "78fc098959a0feb86ab7ca2faa0da499";
    private static final String LOG_TAG  = MainActivity.class.getSimpleName();

    private WeatherModel currentWeather;

    private boolean isWindShown;
    private boolean isCloudsShown;
    private boolean isHumidityShown;
    private boolean isPressureShown;
    private boolean isSunriseShown;
    private boolean isSunsetShown;

    @BindView(R.id.weatherRainContainer) protected     LinearLayout rainLl;
    @BindView(R.id.weatherSnowContainer) protected     LinearLayout snowLl;
    @BindView(R.id.weatherWindContainer) protected     LinearLayout windLl;
    @BindView(R.id.weatherCloudsContainer) protected   LinearLayout cloudsLl;
    @BindView(R.id.weatherHumidityContainer) protected LinearLayout humidityLl;
    @BindView(R.id.weatherPressureContainer) protected LinearLayout pressureLl;
    @BindView(R.id.weatherSunriseContainer) protected  LinearLayout sunriseLl;
    @BindView(R.id.weatherSunsetContainer) protected   LinearLayout sunsetLl;

    @BindView(R.id.weatherCityTv) protected        TextView cityTv;
    @BindView(R.id.weatherCountryTv) protected     TextView countryTv;
    @BindView(R.id.weatherDescriptionTv) protected TextView descriptionTv;

    @BindView(R.id.weatherHourTv) protected TextView hourTv;
    @BindView(R.id.weatherDateTv) protected TextView dateTv;

    @BindView(R.id.weatherTempMinTv) protected TextView tempMinTv;
    @BindView(R.id.weatherTempTv) protected    TextView tempTv;
    @BindView(R.id.weatherTempMaxTv) protected TextView tempMaxTv;

    @BindView(R.id.weatherRainTv) protected     TextView rainTv;
    @BindView(R.id.weatherSnowTv) protected     TextView snowTv;
    @BindView(R.id.weatherWindTv) protected     TextView windTv;
    @BindView(R.id.weatherCloudsTv) protected   TextView cloudsTv;
    @BindView(R.id.weatherPressureTV) protected TextView pressureTv;
    @BindView(R.id.weatherHumidityTv) protected TextView humidityTv;
    @BindView(R.id.weatherSunriseTv) protected  TextView sunriseTv;
    @BindView(R.id.weatherSunsetTv) protected   TextView sunsetTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init ButterKnife
        ButterKnife.bind(this);

        // fetch data
        // TODO: 1/7/2017 make  a method of provider
        CurrentWeatherServiceProvider.getInstance(BASE_URL)
                .getCurrentWeatherService()
                .fetchCurrentWeather("Montreal", "metric")
                .enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        currentWeather = response.body();
                        Log.v(LOG_TAG, "retrofit success!");
                        populateViews(currentWeather);
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        Log.d(LOG_TAG, "retrofit failure", t);
                    }
                });
    }

    private void populateViews(WeatherModel currentWeather) {
        cityTv.setText(currentWeather.name);
        countryTv.setText(currentWeather.sys.country);

        hourTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.dt)); // TODO: 1/7/2017 util to extract time/date
        dateTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.dt));

        tempMinTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.main.temp_min));
        tempTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.main.temp));
        tempMaxTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.main.temp_max));


        pressureTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.main.pressure));
        humidityTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.main.humidity));

        if (currentWeather.wind != null) {
            windTv.setVisibility(View.VISIBLE);
            windTv.setText(String.format(Locale.getDefault(), "%.2f %d", currentWeather.wind.speed, currentWeather.wind.deg));
        } else {
            windTv.setVisibility(View.GONE);
        }

        if (currentWeather.clouds != null) {
            cloudsTv.setVisibility(View.VISIBLE);
            cloudsTv.setText(String.format(Locale.getDefault(), "%d%%", currentWeather.clouds.all));
        } else {
            cloudsTv.setVisibility(View.GONE);
        }

        if (currentWeather.rain != null) {
            rainTv.setVisibility(View.VISIBLE);
            rainTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.rain.threeHours));
        } else {
            rainTv.setVisibility(View.GONE);
        }

        if (currentWeather.snow != null) {
            rainTv.setVisibility(View.VISIBLE);
            snowTv.setText(String.format(Locale.getDefault(), "%.2f", currentWeather.snow.threeHours));
        } else {
            snowTv.setVisibility(View.GONE);
        }

        sunriseTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.sys.sunrise));
        sunsetTv.setText(String.format(Locale.getDefault(), "%d", currentWeather.sys.sunset));
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

        @GET("/data/2.5/weather?appid=" + APP_ID)
        Call<WeatherModel> fetchCurrentWeather(@Query("q") String city, @Query("units") String units);
    }
}