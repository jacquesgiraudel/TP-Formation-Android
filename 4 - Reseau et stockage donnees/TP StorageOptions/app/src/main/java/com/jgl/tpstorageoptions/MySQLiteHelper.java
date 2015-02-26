package com.jgl.tpstorageoptions;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteHelper";

    private static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;

    private static MySQLiteHelper dbHelper;
    private Context context;

    // Database creation sql statement
    private static final String TABLE_CREATE_PRODUCTS = "create table "
            + ProductsContract.TABLE_PRODUCT + "( " + ProductsContract._ID + " long primary key, "
            + ProductsContract.COLUMN_PRODUCT_NAME + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "creation script : " + TABLE_CREATE_PRODUCTS);
        database.execSQL(TABLE_CREATE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + ProductsContract.TABLE_PRODUCT);

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

