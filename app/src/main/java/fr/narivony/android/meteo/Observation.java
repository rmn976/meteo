package fr.narivony.android.meteo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Observation implements Serializable {

    String city;
    int min;
    int max;
    String description;
    String urlIcon;
    transient Bitmap icon;

    public void Observation() {
    }

    @NonNull
    @Override
    public String toString() {
        return city + " : " + min + "°C / " + max + "°C";
    }
}
