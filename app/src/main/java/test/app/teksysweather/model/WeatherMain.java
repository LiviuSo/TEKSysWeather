package test.app.teksysweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lsoco_user on 1/6/2017.
 */
public class WeatherMain {
    @SerializedName("temp") @Expose
    public double temp;

    @SerializedName("pressure") @Expose
    public int pressure;

    @SerializedName("humidity") @Expose
    public int humidity;

    @SerializedName("temp_min") @Expose
    public double temp_min;

    @SerializedName("temp_max") @Expose
    public double temp_max;
}
