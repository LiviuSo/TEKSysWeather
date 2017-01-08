package test.app.teksysweather.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.app.teksysweather.R;
import test.app.teksysweather.model.WeatherModel;
import test.app.teksysweather.util.Utilities;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.activity_splash) protected LinearLayout parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        // test if connected
        if(!Utilities.isNetworkAvailable(this)) {
            Snackbar.make(parentView, "You're offline. Connect and swipe!", Snackbar.LENGTH_LONG).show();
        } else {
            // online
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}