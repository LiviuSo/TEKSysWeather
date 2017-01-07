package test.app.teksysweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lsoco_user on 1/6/2017.
 */
public class Wind {
    @SerializedName("speed") @Expose
    public double speed;

    @SerializedName("deg") @Expose
    public int deg;
}
