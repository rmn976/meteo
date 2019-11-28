package fr.narivony.android.meteo;

import org.json.JSONException;

import java.util.ArrayList;

public interface FournisseurJSON<T> {

    String getURL();

    ArrayList<T> JSON2liste(String strJSON)
        throws JSONException;

}
