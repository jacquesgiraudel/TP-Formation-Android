package com.jgl.mosaic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Classe de gestion de la base de données
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteHelper";

    private static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;

    private static MySQLiteHelper dbHelper;
    private Context context;

    public static final String TABLE_WALLPAPER_IMAGES = "wp_image";

    private static final String TABLE_CREATE_WALLPAPER_IMAGES = "create table "
            + TABLE_WALLPAPER_IMAGES + "( " + MyContentProvider.WallpaperImagesColumns._ID + " long primary key, "
            + MyContentProvider.WallpaperImagesColumns.COLUMN_POSITION + " text not null, "
            + MyContentProvider.WallpaperImagesColumns.COLUMN_IMAGE_LOCATION + " text not null);";

    private MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "creation script : " + TABLE_CREATE_WALLPAPER_IMAGES);
        database.execSQL(TABLE_CREATE_WALLPAPER_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WALLPAPER_IMAGES);

        onCreate(db);
    }

    public static MySQLiteHelper getDbHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = new MySQLiteHelper(context);
        }
        return dbHelper;
    }

    public static SQLiteDatabase getDatabase(Context context){
        return MySQLiteHelper.getDbHelper(context).getReadableDatabase();
    }

}

