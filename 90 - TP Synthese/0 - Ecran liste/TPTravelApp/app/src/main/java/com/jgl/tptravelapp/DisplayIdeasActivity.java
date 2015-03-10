package com.jgl.tptravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jgl.tptravelapp.adapter.IdeaLocalAdapter;
import com.jgl.tptravelapp.model.Idea;

/**
 * Ecran affichant la liste des idées par catégorie
 * Implémentation de l'interface ActionBar.OnNavigationListener (réponse à un changement de sélection dans la barre d'action)
 */
public class DisplayIdeasActivity extends ActionBarActivity implements ActionBar.OnNavigationListener{

    public static final String TAG = "DisplayIdeasActivity";

    private ListView listView;
    private IdeaLocalAdapter mAdapter;

    private int spinnerOptionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate, savedInstanceState null " + (savedInstanceState == null));
        if (savedInstanceState != null){
            Log.d(TAG, "spinnerOptionSelected " + savedInstanceState.getInt("spinnerOptionSelected"));
        }

        // Lien de la classe Activity avec son fichier d'interface graphique (génération de l'arbre de vues déclaré en xml)
        setContentView(R.layout.activity_display_ideas);

        // Récupération de la barre d'action (icône d'application, titre, navigation primaire, menu d'action)
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        // Définition de la navigation primaire : spinner (liste déroulante)
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Création de l'adapter utilisé pour alimenter le spinner
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Source Locale - Cat. A", "Source Web - Cat. B"});
        actionBar.setListNavigationCallbacks(arrayAdapter, this);

        // Récupération par identifiant de l'objet ListView dans l'arbre de vues de l'activité
        listView = (ListView) findViewById(R.id.listView);

        // Création de l'adapters associé à la 1ère option et initialisation
        mAdapter = new IdeaLocalAdapter(this);
        mAdapter.init();

        // Liaison de la ListView à son adapter
        listView.setAdapter(mAdapter);
    }

    /**
     * Méthode appelée sur sélection d'une option dans la liste déroulante
     */
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchissement de l'adapter
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Méthode Standard de génération de l'interface menu (généré par Android Studio)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Création du menu à partir de son fichier d'interface
        getMenuInflater().inflate(R.menu.menu_display_reviews, menu);
        return true;
    }

    /**
     * Méthode Standard d'assignation d'actions aux éléments du menu (généré par Android Studio)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Récupération de l'identifiant associé à l'élément de menu cliqué
        int id = item.getItemId();

        // lien avec l'action associée
        if (id == R.id.action_settings) {
            // Faire ici le traitement correspondant à l'entrée "settings"

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
