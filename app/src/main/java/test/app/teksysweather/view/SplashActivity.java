package test.app.teksysweather.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.app.teksysweather.R;
import test.app.teksysweather.model.WeatherModel;
import test.app.teksysweather.util.Constants;
import test.app.teksysweather.util.Utilities;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.activity_splash) protected LinearLayout parentView;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean hasSp = Utilities.hasSavedSharedPref(this, Constants.SHARED_PREF_WEATHER_MODEL);
        // if not connected and no previous run (on-line), ask the user to connect
        if(!Utilities.isNetworkAvailable(this) && !hasSp) {
            Snackbar.make(parentView, "You're offline. Connect and swipe!", Snackbar.LENGTH_LONG).show();
        } else {
            // online
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) { // left or right swipe detected
                    // if connected, move to main activity
                    if(!Utilities.isNetworkAvailable(this)) {
                        Snackbar.make(parentView, "You're offline. Connect and swipe!", Snackbar.LENGTH_LONG).show();
                    } else {
                        // online
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}