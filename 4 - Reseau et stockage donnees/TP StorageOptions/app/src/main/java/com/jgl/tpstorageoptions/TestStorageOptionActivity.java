package com.jgl.tpstorageoptions;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;


public class TestStorageOptionActivity extends ActionBarActivity {

    private String TAG = "TestStorageOptionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_storage_option);

        SharedPreferences prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("testPref", "test");
        editor.commit();

        File internalBitmapFile = new File(getFilesDir(), "image.jpg");
        Bitmap image = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
        try {
            image.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(internalBitmapFile.getAbsolutePath()));
        }
        catch (Exception e){
            Log.e(TAG, "Erreur dans la création du fichier bitmap sur stockage interne", e);
        }

        File externalPrivateBitmapFile = new File(getExternalFilesDir(null), "image.jpg");
        try {
            image.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(externalPrivateBitmapFile.getAbsolutePath()));
        }
        catch (Exception e){
            Log.e(TAG, "Erreur dans la création du fichier bitmap sur stockage externe, répertoire privé", e);
        }

        File externalPublicBitmapFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image.jpg");
        try {
            image.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(externalPublicBitmapFile.getAbsolutePath()));
            Log.d(TAG, "externalPublicBitmapFile exists ? " + externalPrivateBitmapFile.exists());
        }
        catch (Exception e){
            Log.e(TAG, "Erreur dans la création du fichier bitmap sur stockage externe, répertoire image", e);
        }

        MySQLiteHelper dbHelper = new MySQLiteHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ProductsContract.COLUMN_PRODUCT_NAME, "Product A");
        db.insert(ProductsContract.TABLE_PRODUCT, null, cv);

        cv.put(ProductsContract.COLUMN_PRODUCT_NAME, "Product B");
        db.insert(ProductsContract.TABLE_PRODUCT, null, cv);

        cv.put(ProductsContract.COLUMN_PRODUCT_NAME, "Product C");
        db.insert(ProductsContract.TABLE_PRODUCT, null, cv);

        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_storage_option, menu);
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
}
