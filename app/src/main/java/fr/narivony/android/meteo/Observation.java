package fr.narivony.android.meteo;

import androidx.annotation.NonNull;

public class Observation {

    protected String city;
    protected int min;
    protected int max;
    protected String description;
    protected String urlIcon;

    public void Observation() {
    }

    @NonNull
    @Override
    public String toString() {
        return city + " : " + min + "°C / " + max + "°C";
    }
}
