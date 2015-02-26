package com.jgl.autocatalogueur;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import java.net.URI;

public class AutocatalogueurService extends Service {

    public static final String TAG = "AutocatalogueurService";

    public AutocatalogueurService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Cursor cursor = getContentResolver().query(intent.getData(), new String[]{MediaStore.Images.Media.TITLE, MediaStore.Images.Media.ORIENTATION}, null, null, null);
        if (cursor.moveToFirst()) {
            String title = cursor.getString(0);
            String orientation = cursor.getString(1);
            Log.d(TAG, "orientation " + orientation);
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyContentProvider.TagColumns.COLUMN_TITLE, title);
            contentValues.put(MyContentProvider.TagColumns.COLUMN_TAG, orientation);
            getContentResolver().insert(MyContentProvider.CONTENT_URI, contentValues);
        }

        cursor.close();

        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
