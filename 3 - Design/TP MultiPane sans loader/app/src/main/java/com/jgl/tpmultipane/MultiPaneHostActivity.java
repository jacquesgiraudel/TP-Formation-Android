package com.jgl.tpmultipane;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

/**
 * Activité Hôte des fragments
 */
public class MultiPaneHostActivity extends ActionBarActivity implements ListContactsFragment.Callbacks {

    public static final String TAG = "MultiPaneHostActivity";
    private boolean multiPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Association avec le fichier d'interface descripteur des cadres
        setContentView(R.layout.activity_multi_pane_host);
        // Détermination du mode multicadre basé sur la présence du 2ème fragment
        multiPane = (findViewById(R.id.LC_fragment_detailContact) != null);
    }

    /**
     * Implémentation du callback du fragment liste (permet de traiter les clics effectués sur un de ses éléments)
     */
    @Override
    public void onItemSelected(String id) {

        // Multicadre
        if (multiPane) {
            // MAJ du fragment à partir du nouvel élément
            DetailContactFragment detailFragment = (DetailContactFragment) getFragmentManager().findFragmentById(R.id.LC_fragment_detailContact);
            detailFragment.displayContact(detailFragment.getView(), id);
        }
        // Configuration standard
        else if (!multiPane) {
            // Lancement d'une nouvelle activité détail
            Intent intent = new Intent(this, DetailContactActivity.class);
            intent.putExtra(DetailContactActivity.EXTRA_CONTACT_ID, id);
            startActivity(intent);
        }
    }

}
