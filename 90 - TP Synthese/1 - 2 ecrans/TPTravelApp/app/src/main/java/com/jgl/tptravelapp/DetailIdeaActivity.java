package com.jgl.tptravelapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Ecran détail d'une idée
 */
public class DetailIdeaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_idea);

        // Récupération des vues à initialiser
        ImageView image = (ImageView) findViewById(R.id.ideaImage);
        TextView title = (TextView) findViewById(R.id.ideaTitle);
        TextView description = (TextView) findViewById(R.id.ideaDescription);

        // Récupération des valeurs passées par l'activité appelante
        int ideaDrawableId = getIntent().getIntExtra("imageId", 0);
        String ideaName = getIntent().getStringExtra("name");
        String ideaDescription = getIntent().getStringExtra("description");

        // Initialisation des vues avec ces valeurs
        image.setImageResource(ideaDrawableId);
        title.setText(ideaName);
        description.setText(ideaDescription);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_idea, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}