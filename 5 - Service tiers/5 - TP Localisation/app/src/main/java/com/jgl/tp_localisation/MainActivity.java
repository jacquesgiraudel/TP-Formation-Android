package com.jgl.tp_localisation;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = "MainActivity";

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private TextView mTextView;
    private MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        mTextView = (TextView) findViewById(R.id.textView_iam);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapFragment = MapFragment.newInstance();
                // Ajout du OnMapReadyListener
                mMapFragment.getMapAsync(MainActivity.this);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.linearLayout_root, mMapFragment, "map");
                fragmentTransaction.commit();
            }
        });

    }

    private void displayCurrentAddress() {
        Log.d(TAG, "displayCurrentAddress");
        new AsyncTask<Object, Void, String>() {

            @Override
            protected String doInBackground(Object[] objects) {
                String textAddress = null;
                // Géocodage inverse
                Location location = (Location) objects[0];
                if (location == null) {
                    Log.d(TAG, "location null");
                    return null;
                }
                Geocoder geocoder = new Geocoder(MainActivity.this);
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(
                            location.getLatitude(),
                            location.getLongitude(),
                            // Nombre d'adresses retournées
                            1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses != null & addresses.size() > 0) {
                    Address address = addresses.get(0);
                    textAddress = address.getLocality() + ", " + address.getAddressLine(0);
                }

                return textAddress;
            }

            @Override
            protected void onPostExecute(String textAddress) {
                if (textAddress == null) {
                    textAddress = "inconnu";
                }
                mTextView.setText(getString(R.string.i_am_in, textAddress));
            }
        }.execute(mLastLocation);

        GoogleMap map = mMapFragment.getMap();
        // Ajout d'une info-bulle sur la salle de formation
        MarkerOptions markerOptions = new MarkerOptions().title("Salle de formation").snippet("Formation Android");
        markerOptions.position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        map.addMarker(markerOptions);
        // Déplacement de la caméra sur la position de l'appareil avec un niveau de zoom 15
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 15));

        // Affichage de la position de l'appareil sur la carte
        map.setMyLocationEnabled(true);

    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        if (location != null) {
            mLastLocation = location;
            displayCurrentAddress();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "Map ready");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");

        // Création de la requête de MAJ de données de localisation
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        // Exécution
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "connection failed");
    }

    @Override
    public void onBackPressed() {
        Fragment mapFragment = getFragmentManager().findFragmentByTag("map");
        if (mapFragment != null && mapFragment.isVisible()) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(mapFragment);
            transaction.commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
}
