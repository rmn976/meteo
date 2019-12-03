package fr.narivony.android.meteo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ObservationAdapter extends ArrayAdapter<Observation> {

    private int layoutResourceId;


    ObservationAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        // Stocker le layout
        layoutResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Récupérer l'observation correspondant à cet item
        Observation observation = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // Cet item n'est pas recyclé, il faut inflater son layout
            Log.d("Adapter", "inflate");
            convertView = LayoutInflater.from(getContext()).inflate(layoutResourceId, parent, false);
        } else {
            Log.d("Adapter", "recycle");
        }
        // Définir l'icône
        ImageView icon = convertView.findViewById(R.id.item_icon);
        TextView text = convertView.findViewById(R.id.item_text);
        TextView itemDescription = convertView.findViewById(R.id.item_description);
        // Populate the data into the template view using the data object
        assert observation != null;
        icon.setImageBitmap(observation.icon);
        text.setText(observation.toString());



        if (itemDescription != null) {
            itemDescription.setText(getContext().getString(R.string.description, observation.description));
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
