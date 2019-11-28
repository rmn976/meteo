package fr.narivony.android.meteo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OWM implements FournisseurJSON {

    private final static String URL = "https://api.openweathermap.org/data/2.5/group?id=264371,2950159,3060972,2800866,3054643,2618425,2964574,658225,2267057,3196359,3117735,2988507,3067696,456172,3169070,2673730,588409,756135,2761369,593116&units=metric&lang=fr&mode=json";
    private final static String KEY = "cc4c74e4ea08d0cbbd9f2a92cfb5fbbf";
    private final static String ICON_BASE_URL = "https://openweathermap.org/img/w/";
    private final static String ICON_EXTENSION = ".png";



    @Override
    public String getURL() {
        return URL + "&appid=" + KEY;
    }

    @Override
    public ArrayList<Observation> JSON2liste(String strJSON) throws JSONException {

        // Créer la liste
        ArrayList<Observation> list = new ArrayList<>();
        // Préparer l'extraction des données
        JSONObject root = new JSONObject(strJSON);
        JSONArray citiesArray = root.getJSONArray("list");

        for(int i=0; i<citiesArray.length(); i++) {
            Observation observation = new Observation();
            JSONObject cityObject = citiesArray.getJSONObject(i);
            observation.city = cityObject.getString("name");

            JSONObject mainObject = cityObject.getJSONObject("main");
            observation.min = (int) Math.round(mainObject.getDouble("temp_min"));
            observation.max = (int) Math.round(mainObject.getDouble("temp_max"));

            JSONObject weatherObject = cityObject.getJSONArray("weather").getJSONObject(0);
            observation.description = weatherObject.getString("description");
            observation.urlIcon = ICON_BASE_URL + weatherObject.getString("icon") + ICON_EXTENSION;

            list.add(observation);

        }
        return list;
    }
}
