package com.jgl.tptravelapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgl.tptravelapp.adapter.IdeaWebAdapter;
import com.jgl.tptravelapp.model.MySQLiteOpenHelper;

/**
 * Ecran détail d'une idée
 */
public class DetailIdeaActivity extends ActionBarActivity {

    private ImageButton imageButton;
    private MySQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_idea);

        // Initialisation du helper de base de données
        dbHelper = MySQLiteOpenHelper.getInstance(this);

        // Récupération des vues à initialiser
        ImageView image = (ImageView) findViewById(R.id.ideaImage);
        TextView title = (TextView) findViewById(R.id.ideaTitle);
        TextView description = (TextView) findViewById(R.id.ideaDescription);
        imageButton = (ImageButton) findViewById(R.id.bookmarkButton);

        // Récupération des valeurs passées par l'activité appelante
        int ideaDrawableId = getIntent().getIntExtra("imageId", 0);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String ideaName = getIntent().getStringExtra("name");
        String ideaDescription = getIntent().getStringExtra("description");

        // Initialisation des vues avec ces valeurs
        if (ideaDrawableId != 0) {
            image.setImageResource(ideaDrawableId);
        }
        else if (imageUrl != null){
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                IdeaWebAdapter.LoadImageTask loadImageTask = new IdeaWebAdapter.LoadImageTask(image);
                loadImageTask.execute(imageUrl);
            }
        }
        title.setText(ideaName);
        description.setText(ideaDescription);

        initBookmarkState();
    }

    /**
     * Initialisation de l'image du bouton image de favori
     */
    private void initBookmarkState(){
        if (dbHelper.isABookmark(getIntent().getStringExtra("name"))) {
            imageButton.setImageResource(R.drawable.ic_heart);
        }
        else if (!dbHelper.isABookmark(getIntent().getStringExtra("name"))) {
            imageButton.setImageResource(R.drawable.ic_heart_outline);
        }
    }

    /**
     * Change l'état du favori (l'ajoute ou le supprime)
     */
    public void toggleBookmark(View view){
        dbHelper.toggleBookmark(getIntent().getStringExtra("name"));
        initBookmarkState();
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