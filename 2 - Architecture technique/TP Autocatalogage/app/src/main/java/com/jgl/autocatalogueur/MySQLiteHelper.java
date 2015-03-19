package com.jgl.autocatalogueur;

import android.database.Cursor;

import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
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

    public static final String TABLE_TAGS = "tags";

    private static final String TABLE_CREATE_TAGS = "create table "
            + TABLE_TAGS + "( " + MyContentProvider.TagColumns._ID + " long primary key, "
            + MyContentProvider.TagColumns.COLUMN_TITLE + " text not null, "
            + MyContentProvider.TagColumns.COLUMN_TAG + " text not null);";

    private MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "creation script : " + TABLE_CREATE_TAGS);
        database.execSQL(TABLE_CREATE_TAGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS);

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

