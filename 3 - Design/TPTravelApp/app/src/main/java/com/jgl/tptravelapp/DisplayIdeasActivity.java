package com.jgl.tptravelapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.jgl.tptravelapp.model.Idea;


public class DisplayIdeasActivity extends ActionBarActivity implements ActionBar.OnNavigationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ideas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        // Définition de la navigation primaire : spinner (Listé déroulante)
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Création de l'adapter utilisé pour alimenter le spinner
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Source Locale", "Source Web"});
        SpinnerAdapter adapter = arrayAdapter;
        actionBar.setListNavigationCallbacks(adapter, this);

        // Création et assignation de l'adapter utilisé par la liste d'idées
        ListView listView = (ListView) findViewById(R.id.listView);
        final IdeaAdapter cardAdapter = new IdeaAdapter(this);
        cardAdapter.init();
        listView.setAdapter(cardAdapter);

        // Assignation d'un comportement au clic aux éléments de la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Idea idea = (Idea) cardAdapter.getItem(i);

                Intent intent = new Intent(DisplayIdeasActivity.this, DetailIdeaActivity.class);
                intent.putExtra("name", idea.getIdeaName());
                intent.putExtra("description", idea.getIdeaDescription());
                intent.putExtra("image", idea.getIdeaDrawableId());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_reviews, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {

        return false;
    }
}
