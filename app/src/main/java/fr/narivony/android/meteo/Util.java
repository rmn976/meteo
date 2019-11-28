package fr.narivony.android.meteo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public final class Util {

    public static Boolean isConnected(Context context) {
        //Vérifier la connexion internet
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        Network[] networks = cm.getAllNetworks();
        if (networks.length > 0){
            for (Network network :networks){
                NetworkCapabilities nc = cm.getNetworkCapabilities(network);
                if (nc != null && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    return true;
                }
            }
        }
        return false;
    }
}
