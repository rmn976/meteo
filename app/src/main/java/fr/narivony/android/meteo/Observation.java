package fr.narivony.android.meteo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Observation implements Serializable {

    protected String city;
    protected int min;
    protected int max;
    protected String description;
    protected String urlIcon;
    protected transient Bitmap icon;

    public void Observation() {
    }

    @NonNull
    @Override
    public String toString() {
        return city + " : " + min + "°C / " + max + "°C";
    }
}
