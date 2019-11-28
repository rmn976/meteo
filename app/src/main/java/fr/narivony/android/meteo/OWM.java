package fr.narivony.android.meteo;

import org.json.JSONException;

import java.util.ArrayList;

public class OWM implements FournisseurJSON {

    private final static String URL = "https://api.openweathermap.org/data/2.5/group?id=264371,2950159,3060972,2800866,3054643,2618425,2964574,658225,2267057,3196359,3117735,2988507,3067696,456172,3169070,2673730,588409,756135,2761369,593116&units=metric&lang=fr&mode=json";
    private final static String OWM_API_KEY = "cc4c74e4ea08d0cbbd9f2a92cfb5fbbf";



    @Override
    public String getURL() {
        return OWM_API_URL + "&appid=" + OWM_API_KEY;
    }

    @Override
    public ArrayList<String> JSON2liste(String strJSON) throws JSONException {
        return null;
    }
}
