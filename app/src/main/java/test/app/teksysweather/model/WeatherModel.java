package test.app.teksysweather.model;

import android.support.annotation.StringRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lsoco_user on 1/6/2017.
 */
public class WeatherModel {
    @SerializedName("coord") @Expose
    public Coord coord;

    @SerializedName("weather") @Expose
    public List<Weather> weather;

//    @SerializedName("base") @Expose
//    public String base;

    @SerializedName("main") @Expose
    public WeatherMain main;

    @SerializedName("visibility") @Expose
    public int visibility;

    @SerializedName("wind") @Expose
    public Wind wind;

    @SerializedName("clouds") @Expose
    public Clouds clouds;

    @SerializedName("rain") @Expose
    public Precipitation rain;

    @SerializedName("snow") @Expose
    public Precipitation snow;

    @SerializedName("dt") @Expose
    public int dt;

    @SerializedName("sys") @Expose
    public Sys sys;

    @SerializedName("id") @Expose
    public int id;

    @SerializedName("name") @Expose
    public String name;

//    @SerializedName("cod") @Expose
//    public String cod;
}
