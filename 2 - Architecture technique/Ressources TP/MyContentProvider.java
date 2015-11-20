package com.jgl.mosaic;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Implémentation de notre ContentProvider
 */
public class MyContentProvider extends ContentProvider {

    public static final String LOG_TAG = "MyContentProvider";
    public static final String AUTHORITY = "com.jgl.mosaic";
    public static final String URI_WP_IMAGES = "wp_images";

    public static final String TABLE_WALLPAPER_IMAGES = "wp_images";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + URI_WP_IMAGES);
    public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_WALLPAPER_IMAGES;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_WALLPAPER_IMAGES;
    private static final int IMAGE_LIST = 1;
    private static final int IMAGE_ID = 2;

    private static final UriMatcher uriMatcher;
    // prepare the UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MyContentProvider.AUTHORITY,
                URI_WP_IMAGES,
                IMAGE_LIST);
        uriMatcher.addURI(MyContentProvider.AUTHORITY,
                URI_WP_IMAGES + "/#",
                IMAGE_ID);
    }

    public MyContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase readableDatabase = MySQLiteHelper.getDatabase(getContext());
        int res = readableDatabase.delete(MySQLiteHelper.TABLE_WALLPAPER_IMAGES, selection, selectionArgs);

        return res;
    }

    @Override
    public String getType(Uri uri) {
        String type = null;

        switch (uriMatcher.match(uri)){
            case IMAGE_LIST:
                type =  CONTENT_DIR_TYPE;
                break;
            case IMAGE_ID:
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
        long rowId = readableDatabase.insert(MySQLiteHelper.TABLE_WALLPAPER_IMAGES, null, values);

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
        Cursor cursor = database.query(MySQLiteHelper.TABLE_WALLPAPER_IMAGES, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase database = MySQLiteHelper.getDatabase(getContext());
        int res = database.update(MySQLiteHelper.TABLE_WALLPAPER_IMAGES, values, selection, selectionArgs);

        return res;
    }



    public interface WallpaperImagesColumns extends BaseColumns {
        public static final String COLUMN_POSITION = "position";
        public static final String COLUMN_IMAGE_LOCATION = "image_location";
    }

}
