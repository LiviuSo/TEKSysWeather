package test.app.teksysweather.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.app.teksysweather.R;
import test.app.teksysweather.model.WeatherModel;
import test.app.teksysweather.service.CurrentWeatherServiceProvider;
import test.app.teksysweather.service.WeatherPresenter;

import static test.app.teksysweather.util.Constants.BASE_URL;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private WeatherModel     currentWeather;
    private WeatherPresenter weatherPresenter;

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

        // set to 'visible' the optional items
        enableOptionals();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // fetch data
        // TODO: 1/7/2017 make  a method of provider
        CurrentWeatherServiceProvider.getInstance(BASE_URL)
                .getCurrentWeatherService()
                .fetchCurrentWeather("Montreal", "metric")
                .enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        currentWeather = response.body();
                        weatherPresenter = new WeatherPresenter(currentWeather);
                        Log.v(LOG_TAG, "retrofit success!");
                        populateViews(currentWeather);
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        Log.d(LOG_TAG, "retrofit failure", t);
                    }
                });
    }

    private void enableOptionals() {
        isWindShown = true;
        isCloudsShown = true;
        isHumidityShown = true;
        isPressureShown = true;
        isSunriseShown = true;
        isSunsetShown = true;
    }

    private void populateViews(WeatherModel currentWeather) {
        // show location
        weatherPresenter.showLocation(cityTv, countryTv);
        // show hour/date
        weatherPresenter.showTime(hourTv, dateTv);
        // show temp
        weatherPresenter.showTemperature(tempMinTv, tempTv, tempMaxTv);
        // show rain (if available)
        weatherPresenter.showRain(rainTv);
        // show snow
        weatherPresenter.showSnow(snowTv);
        // show wind
        weatherPresenter.showWind(windTv, isWindShown);
        // show clouds
        weatherPresenter.showClouds(cloudsTv, isCloudsShown);
        // show pressure
        weatherPresenter.showPressure(pressureTv, isPressureShown);
        // show humidity
        weatherPresenter.showHumidity(humidityTv, isHumidityShown);
        // show sunrise
        weatherPresenter.showSunrise(sunriseTv, isSunriseShown);
        // show sunset
        weatherPresenter.showSunset(sunsetTv, isSunsetShown);
    }
}