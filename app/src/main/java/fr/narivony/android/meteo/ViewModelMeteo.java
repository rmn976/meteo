package fr.narivony.android.meteo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewModelMeteo extends AndroidViewModel {

    final static String NO_INTERNET_CONNECTION = "NO_INTERNET_CONNECTION";
    final static String LOADING_ON = "LOADING_ON";
    final static String LOADING_OFF = "LOADING_OFF";
    private MutableLiveData<ArrayList<Observation>> mldObservationsList;
    private MutableLiveData<String> state = new MutableLiveData<>();
    private Application application;

    public ViewModelMeteo(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    LiveData<String> getState() {
        return state;
    }

    LiveData<ArrayList<Observation>> getObservations() {

        if (mldObservationsList == null) {
            mldObservationsList = new MutableLiveData<>();
            loadObservations();
        }

        return mldObservationsList;
    }

    private void loadObservations() {

        // Si connexion internet, alors se connecter à OWM
        if (Util.isConnected(application)) {
            new AsyncTaskMeteo().execute(new OWM().getURL());
        } else {
            state.setValue(NO_INTERNET_CONNECTION);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskMeteo extends AsyncTask<String, Void, ArrayList<Observation>> {

        @Override
        protected void onPreExecute() {

            state.setValue(LOADING_ON);
        }

        @Override
        protected ArrayList<Observation> doInBackground(String... strings) {

            // Tenter de récupérer le JSON retourné par OWM
            InputStream is;
            try {
                is = new URL(strings[0]).openStream();
            } catch (IOException e) {
                Log.e("OWM", application.getString(R.string.error_owm_connexion));
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
                Log.e("OWM", application.getString(R.string.error_owm_json));
                return null;
            }
            for (Observation observation : observationsList) {
                InputStream isImage;
                try {
                    isImage = new URL(observation.urlIcon).openStream();
                    observation.icon = BitmapFactory.decodeStream(isImage);
                } catch (IOException e) {
                    Log.e("OWM", application.getString(R.string.error_icon_not_found));
                }
            }
            return observationsList;
        }

        @Override
        protected void onPostExecute(ArrayList<Observation> list) {

            mldObservationsList.setValue(list);
            state.setValue(LOADING_OFF);
        }
    }
}
