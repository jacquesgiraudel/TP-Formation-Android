package com.jgl.autocatalogueur;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Ecran : liste des photos prises par orientation
 */
public class ListCategoriesActivity extends ActionBarActivity {

    private TextView portraitListTextView;
    private TextView landscapeListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_categories);

        // Affiche les images en portrait
        portraitListTextView = (TextView) findViewById(R.id.portraitImages);
        displayTagsInTextView(portraitListTextView, new String[]{"0", "180"});

        // Affiche les images en mode paysage
        landscapeListTextView = (TextView) findViewById(R.id.landscapeImages);
        displayTagsInTextView(landscapeListTextView, new String[]{"90", "270"});
    }

    /**
     * Affichage de la liste des images pour une catégorie
     */
    private void displayTagsInTextView(TextView textView, String[] tags){
        String selectionClause = MyContentProvider.TagColumns.COLUMN_TAG + " = ? OR " + MyContentProvider.TagColumns.COLUMN_TAG + " = ? ";
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, new String[]{MyContentProvider.TagColumns.COLUMN_TITLE}, selectionClause, tags, null);
        String list = "";
        if (cursor.moveToFirst()) {
            do {
                list += cursor.getString(0) + ", ";
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        textView.setText(list);
    }

    /**
     * Rafraîchit l'écran sur retour à l'application
     */
    @Override
    protected void onResume() {
        super.onResume();

        displayTagsInTextView(portraitListTextView, new String[]{"0", "180"});
        displayTagsInTextView(landscapeListTextView, new String[]{"90", "270"});
    }

}
