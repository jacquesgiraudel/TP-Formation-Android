package com.jgl.autocatalogueur;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implémentation de notre ContentProvider
 */
public class MyContentProvider extends ContentProvider {

    public static final String LOG_TAG = "MyContentProvider";
    public static final String AUTHORITY = "com.jgl.autocatalogueur";
    public static final String URI_TAGS = "tags";

    public static final String TABLE_TAG = "tag";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + URI_TAGS);
    public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_TAG;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_TAG;
    private static final int TAG_LIST = 1;
    private static final int TAG_ID = 2;

    private static final UriMatcher uriMatcher;
    // prepare the UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MyContentProvider.AUTHORITY,
                URI_TAGS,
                TAG_LIST);
        uriMatcher.addURI(MyContentProvider.AUTHORITY,
                URI_TAGS + "/#",
                TAG_ID);
    }

    public MyContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase readableDatabase = MySQLiteHelper.getDatabase(getContext());
        int res = readableDatabase.delete(MySQLiteHelper.TABLE_TAGS, selection, selectionArgs);

        return res;
    }

    @Override
    public String getType(Uri uri) {
        String type = null;

        switch (uriMatcher.match(uri)){
            case TAG_LIST :
                type =  CONTENT_DIR_TYPE;
                break;
            case TAG_ID :
                type =  CONTENT_ITEM_TYPE;
                break;
            default:
                type = null;
        }

        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase readableDatabase = MySQLiteHelper.getDatabase(getContext());
        long rowId = readableDatabase.insert(MySQLiteHelper.TABLE_TAGS, null, values);

        if (rowId != -1){
            Log.d(LOG_TAG, "insertion in " + LOG_TAG + " succeed");
        }

        return Uri.parse(CONTENT_URI + "/" + rowId);
    }

    @Override
    public boolean onCreate() {

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = MySQLiteHelper.getDatabase(getContext());
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TAGS, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase database = MySQLiteHelper.getDatabase(getContext());
        int res = database.update(MySQLiteHelper.TABLE_TAGS, values, selection, selectionArgs);

        return res;
    }

    public interface TagColumns extends BaseColumns {
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TAG = "tag";
    }

}
