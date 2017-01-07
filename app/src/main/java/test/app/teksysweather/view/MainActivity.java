package test.app.teksysweather.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private String  units;
    private String  cityName;

    @BindView(R.id.weatherRainContainer) protected     LinearLayout rainLl;
    @BindView(R.id.weatherSnowContainer) protected     LinearLayout snowLl;
    @BindView(R.id.weatherWindContainer) protected     LinearLayout windLl;
    @BindView(R.id.weatherCloudsContainer) protected   LinearLayout cloudsLl;
    @BindView(R.id.weatherHumidityContainer) protected LinearLayout humidityLl;
    @BindView(R.id.weatherPressureContainer) protected LinearLayout pressureLl;
    @BindView(R.id.weatherSunriseContainer) protected  LinearLayout sunriseLl;
    @BindView(R.id.weatherSunsetContainer) protected   LinearLayout sunsetLl;

    @BindView(R.id.weatherIcon) protected          ImageView iconIv;
    @BindView(R.id.weatherCityTv) protected        TextView  cityTv;
    @BindView(R.id.weatherCountryTv) protected     TextView  countryTv;
    @BindView(R.id.weatherDescriptionTv) protected TextView  descriptionTv;

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

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Current Weather");
        }

        // init ButterKnife
        ButterKnife.bind(this);

        // set to 'visible' the optional items
        readOptions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // fetch data
        fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int actId = item.getItemId();

        switch (actId) {
            case R.id.weatherMap:
                showLocation();
                break;
            case R.id.weatherRefresh:
                fetchData();
                break;
            case R.id.weatherSetting:
                Log.v(LOG_TAG, "settings");
//                Intent intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
                break;
            default: return false;
        }

        return true;
    }

    @OnClick(R.id.weatherNewCity)
    public void onEnterNewCity() {
        Log.v(LOG_TAG, "new city");
        // launch an edit dialog
        launchEditDialog();
    }

    private void launchEditDialog() {
        final EditText editText = new EditText(MainActivity.this);

        new AlertDialog.Builder(this)
                .setTitle("City name")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cityName = editText.getText().toString();
                        Log.v(LOG_TAG, cityName);
                        fetchData();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }

    public void fetchData() {
        CurrentWeatherServiceProvider.getInstance(BASE_URL)
                .getCurrentWeatherService()
                .fetchCurrentWeather(cityName, units)
                .enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        currentWeather = response.body();
                        weatherPresenter = new WeatherPresenter(currentWeather);
                        Log.v(LOG_TAG, "retrofit success!");
                        populateViews();
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        Log.d(LOG_TAG, "retrofit failure", t);
                    }
                });
    }

    private void readOptions() {
        isWindShown = true;
        isCloudsShown = true;
        isHumidityShown = true;
        isPressureShown = true;
        isSunriseShown = true;
        isSunsetShown = true;
        units = "metric";
        cityName = "Laval";
    }

    private void populateViews() {
        Log.v(LOG_TAG, "populateViews()");
        // show icon
        weatherPresenter.showIcon(iconIv);
        // show location
        weatherPresenter.showLocation(cityTv, countryTv);
        // show description
        weatherPresenter.showDescription(descriptionTv);
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

    private void showLocation() {
        String latLon = String.format(Locale.getDefault(),
                                      "%f,%f(%s)",
                                      currentWeather.coord.lat, currentWeather.coord.lon, currentWeather.name);
        Uri uri = Uri.parse("geo:0,0").buildUpon()
                .appendQueryParameter("q", latLon)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        // test if any app can handle the intent & show the location
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.e(LOG_TAG, "No activity to show the map");
        }
    }

}