package test.app.teksysweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lsoco_user on 1/6/2017.
 */
public class Sys {
    @SerializedName("type") @Expose
    public int type;

    @SerializedName("id") @Expose
    public int id;

    @SerializedName("message") @Expose
    public double message;

    @SerializedName("country") @Expose
    public String country;

    @SerializedName("sunrise") @Expose
    public int sunrise;

    @SerializedName("sunset") @Expose
    public int sunset;
}
