package test.app.teksysweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lsoco_user on 1/6/2017.
 */
public class Weather {
    @SerializedName("id") @Expose
    public int id;

    @SerializedName("main") @Expose
    public String main;

    @SerializedName("description") @Expose
    public String description;

    @SerializedName("icon") @Expose
    public String icon;
}
