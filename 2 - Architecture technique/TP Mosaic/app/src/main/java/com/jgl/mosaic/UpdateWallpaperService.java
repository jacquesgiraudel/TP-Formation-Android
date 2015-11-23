package com.jgl.mosaic;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Composant service : détermination de l'orientation de l'image et catalogage
 */
public class UpdateWallpaperService extends Service {

    public static final String TAG = "AutocatalogueurService";

    public UpdateWallpaperService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Récupération de l'orientation à partir du ContentProvider Media (informations sur les médias de l'appareil : image, musique, etc.)
        Cursor cursor = getContentResolver().query(intent.getData(), new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor.moveToFirst()) {
            String thumbnailPath = cursor.getString(0);
            Log.d(TAG, "Chemin du thumbnail : " + thumbnailPath);

            addImageToMosaic(thumbnailPath);

            WallpaperUtils.displayMosaicWallpaper(getApplicationContext());
        }

        cursor.close();

        return START_NOT_STICKY;
    }

    private void addImageToMosaic(String imagePath){
        String sortClause = MyContentProvider.WallpaperImagesColumns.COLUMN_POSITION + " asc";

        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, new String[]{MyContentProvider.WallpaperImagesColumns.COLUMN_IMAGE_LOCATION}, null, null, sortClause);

        int positionsFilled = cursor.getCount();

        if (positionsFilled < 4){
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyContentProvider.WallpaperImagesColumns.COLUMN_POSITION, positionsFilled + 1);
            contentValues.put(MyContentProvider.WallpaperImagesColumns.COLUMN_IMAGE_LOCATION, imagePath);
            getContentResolver().insert(MyContentProvider.CONTENT_URI, contentValues);
        }
        else {
            cursor.moveToLast();

            String whereClause = MyContentProvider.WallpaperImagesColumns.COLUMN_POSITION + " = ?";
            String[] selectionArgs = null;
            int positionToUpdate;

            while (cursor.moveToPrevious()){
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyContentProvider.WallpaperImagesColumns.COLUMN_IMAGE_LOCATION, cursor.getString(0));

                positionToUpdate = cursor.getPosition() + 2;
                selectionArgs = new String[]{String.valueOf(positionToUpdate)};

                getContentResolver().update(MyContentProvider.CONTENT_URI, contentValues, whereClause, selectionArgs);
            }

            positionToUpdate = 1;
            selectionArgs = new String[]{String.valueOf(positionToUpdate)};
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyContentProvider.WallpaperImagesColumns.COLUMN_IMAGE_LOCATION, imagePath);
            getContentResolver().update(MyContentProvider.CONTENT_URI, contentValues, whereClause, selectionArgs);
        }

        cursor.close();
    }

    private void insertWallpaperImage(int position, String thumbnailPath){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyContentProvider.WallpaperImagesColumns.COLUMN_POSITION, position);
        contentValues.put(MyContentProvider.WallpaperImagesColumns.COLUMN_IMAGE_LOCATION, thumbnailPath);
        getContentResolver().insert(MyContentProvider.CONTENT_URI, contentValues);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
