package fr.narivony.android.meteo;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class FragmentList extends Fragment {

    final static String OWM_API_URL = "https://api.openweathermap.org/data/2.5/group?id=264371,2950159,3060972,2800866,3054643,2618425,2964574,658225,2267057,3196359,3117735,2988507,3067696,456172,3169070,2673730,588409,756135,2761369,593116&units=metric&lang=fr&mode=json";
    final static String OWM_API_KEY = "cc4c74e4ea08d0cbbd9f2a92cfb5fbbf";


    public FragmentList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflater le layout du fragment de la liste
        // Ne pas l'attacher au ViewGroup pour permettre les transactions par programmation
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Récupérer le composant ListView depuis le layout
        ListView lv = view.findViewById(R.id.list);
        // Créer un adapteur de string
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.item);
        // Associer l'adapter à la liste
        lv.setAdapter(adapter);

        //Si connexion internet, alors se connecter à OWM
        if (Util.isConnected(getContext())) {
            String url = OWM_API_URL + "&appid=" + OWM_API_KEY;
            new AsyncTaskMeteo().execute(url);
        } else {
            Snackbar.make(getActivity().findViewById(R.id.content), R.string.error_internet_connexion, Snackbar.LENGTH_LONG).show();
        }


        return view;
    }

    private class AsyncTaskMeteo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            //Tenter de récupérer le JSON retourné par OWM
            InputStream is;
            try {
                is = new URL(strings[0]).openStream();
            } catch (IOException e) {
                Snackbar.make(getActivity().findViewById(R.id.content), R.string.error_owm_connexion, Snackbar.LENGTH_LONG).show();
                return null;
            }
            Scanner sc = new Scanner(is);
            StringBuilder sbJSON = new StringBuilder(10000);
            while (sc.hasNextLine()) {
                sbJSON.append(sc.nextLine());
            }
            sc.close();
            return sbJSON.toString();
        }

        @Override
        protected void onPostExecute(String strJSON) {
            //Log.d("JSON", strJSON);
        }
    }

}
