package fr.narivony.android.meteo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class FragmentList extends Fragment {

    private ViewModelMeteo vmMeteo;

    public FragmentList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmMeteo = new ViewModelProvider(requireActivity()).get(ViewModelMeteo.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflater le layout du fragment de la liste
        // Ne pas l'attacher au ViewGroup pour permettre les transactions par programmation
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        // Récupérer le composant ListView depuis le layout
        ListView lv = view.findViewById(R.id.list);
        // Create the adapter to convert the array to views
        ObservationAdapter adapter = new ObservationAdapter(getContext(), R.layout.item);
        // Associer l'adapter à la liste
        lv.setAdapter(adapter);

        // Ecouter vmMeteo pour les observations
        // A chaque changement de viewModelMeteo, cela se répercute ici (pas de rechargement car sauvegardé). Ceci est comme un écouteur
        vmMeteo.getObservations().observe(getViewLifecycleOwner(), observations -> {
            // Purger l'adapter
            adapter.clear();
            // Peupler l'adapter
            adapter.addAll(observations);
        });

        // Retourner le composant ListView
        return view;
    }
}
