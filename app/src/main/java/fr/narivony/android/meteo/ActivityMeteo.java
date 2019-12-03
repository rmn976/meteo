package fr.narivony.android.meteo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

public class ActivityMeteo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Définir le layout
        setContentView(R.layout.activity_meteo);

        // Supporter l'ActionBar via une Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialiser la snackbar d'attente
        Snackbar wait = Snackbar.make(findViewById(R.id.content), R.string.loading_observation_content, Snackbar.LENGTH_INDEFINITE);
        // Récupérer une instance de ViewModelMeteo
        ViewModelMeteo vmMeteo = new ViewModelProvider(this).get(ViewModelMeteo.class);
        // On observe le state de ViewModelMeteo
        vmMeteo.getState().observe(this, state -> {
            if (state.equals(ViewModelMeteo.NO_INTERNET_CONNECTION)) {
                Snackbar.make(findViewById(R.id.content), R.string.error_internet_connexion, Snackbar.LENGTH_LONG).show();
            } else if (state.equals(ViewModelMeteo.LOADING_ON)) {
                wait.show();
            } else if (state.equals(ViewModelMeteo.LOADING_OFF)) {
                wait.dismiss();
            }
        });

        // Charger une instance de FragmentList
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, new FragmentList());
        ft.commit();
    }
}
