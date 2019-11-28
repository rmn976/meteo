package fr.narivony.android.meteo;


import android.graphics.BitmapFactory;
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

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class FragmentList extends Fragment {


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
            new AsyncTaskMeteo().execute(new OWM().getURL());
        } else {
            Snackbar.make(getActivity().findViewById(R.id.content), R.string.error_internet_connexion, Snackbar.LENGTH_LONG).show();
        }


        return view;
    }

    private class AsyncTaskMeteo extends AsyncTask<String, Void, ArrayList<Observation>> {

        @Override
        protected ArrayList<Observation> doInBackground(String... strings) {
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
            ArrayList<Observation> observationsList;
            try {
                observationsList = new OWM().JSON2liste(sbJSON.toString());
            } catch (JSONException e) {
                Snackbar.make(getActivity().findViewById(R.id.content), R.string.error_owm_json, Snackbar.LENGTH_LONG).show();
                return null;
            }
            for (Observation observation : observationsList) {
                InputStream isImage;
                try {
                    isImage = new URL(observation.urlIcon).openStream();
                    observation.icon = BitmapFactory.decodeStream(isImage);
                } catch (IOException e) {
                    Log.e("Icone", getString(R.string.error_icon_not_found));
                }
            }
            return observationsList;
        }

        @Override
        protected void onPostExecute(ArrayList<Observation> list) {

        }
    }

}
