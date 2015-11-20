package com.jgl.mosaic;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Ecran : liste des photos prises par orientation
 */
public class ListWallpaperImagesActivity extends ActionBarActivity {

    private TextView wallpaperImagesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wallpaper_images);

        // Affiche la liste des images du fond d'écran
        wallpaperImagesTextView = (TextView) findViewById(R.id.wallpaper_images);
        displayWallpaperImagesInTextView(wallpaperImagesTextView);
    }

    /**
     * Affichage de la liste des images constituant le fond d'écran
     */
    private void displayWallpaperImagesInTextView(TextView textView){
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, new String[]{MyContentProvider.WallpaperImagesColumns.COLUMN_POSITION, MyContentProvider.WallpaperImagesColumns.COLUMN_IMAGE_LOCATION}, null, null, null);
        String list = "";
        if (cursor.moveToFirst()) {
            do {
                list += cursor.getString(0) + " : " + cursor.getString(1) + "\n";
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

        displayWallpaperImagesInTextView(wallpaperImagesTextView);
    }

}
