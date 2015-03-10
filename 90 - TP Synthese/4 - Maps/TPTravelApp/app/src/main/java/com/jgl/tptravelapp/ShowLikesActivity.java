package com.jgl.tptravelapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Carte affichant des marqueurs sur les géocodes passés en entrée
 */
public class ShowLikesActivity extends FragmentActivity {

    public static final String TAG = "ShowLikesActivity";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_likes);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Initialise la carte si accessible et si requis
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * Configuration de la carte (marqueurs, déplacement caméra, etc.)
     */
    private void setUpMap() {
        double[] lats = getIntent().getDoubleArrayExtra("lats");
        double[] longs = getIntent().getDoubleArrayExtra("longs");

        // Récupération du géocodeur (géocodage et géocodage inverse)
        Geocoder geocoder = new Geocoder(this, Locale.FRENCH);

        if (lats != null && longs != null) {
            // Affichage d'un marqueur par géocode
            for (int i = 0; i < lats.length; i++) {
                // Géocodage inverse (géocode -> adresse)
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(
                            lats[i],
                            longs[i],
                            // Nombre d'adresse retourné
                            1);
                } catch (IOException e) {
                    Log.e(TAG, "Erreur pendant le chargement des marqueurs", e);
                }
                String address = getString(R.string.unknown_address);
                if (addresses != null && addresses.size() != 0) {
                    address = testAddressField(addresses.get(0).getLocality()) + ", " + testAddressField(addresses.get(0).getCountryName());
                }

                // Ajout du marqueur
                mMap.addMarker(new MarkerOptions().position(new LatLng(lats[i],
                        longs[i])).title(address));
            }
        }
    }

    /**
     * Retourne un chaîne par défaut si champ d'adresse vide
     */
    private String testAddressField(String field){
        if (field == null){
            field = getString(R.string.unknown_address);
        }

        return field;
    }
}
