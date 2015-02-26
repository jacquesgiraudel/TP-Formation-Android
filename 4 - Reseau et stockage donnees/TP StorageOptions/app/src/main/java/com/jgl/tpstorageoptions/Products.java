package com.jgl.tpstorageoptions;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class Products extends ContentProvider {

    public static final String TAG = "Products";

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    // prepare the UriMatcher
    static {

        uriMatcher.addURI(ProductsContract.AUTHORITY,
                ProductsContract.TABLE_PRODUCT,
                ProductsContract.TABLE_PRODUCT_LIST);
        uriMatcher.addURI(ProductsContract.AUTHORITY,
                ProductsContract.TABLE_PRODUCT + "/#",
                ProductsContract.TABLE_PRODUCT_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = MySQLiteHelper.getDatabase(getContext());
        Cursor cursor = database.query(ProductsContract.TABLE_PRODUCT, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        String type = null;

        switch (uriMatcher.match(uri)){
            case ProductsContract.TABLE_PRODUCT_LIST :
                type =  ProductsContract.PRODUCT_DIR_TYPE;
                break;
            case ProductsContract.TABLE_PRODUCT_ID :
                type =  ProductsContract.PRODUCT_ITEM_TYPE;
                break;
            default:
                type = null;
        }

        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase readableDatabase = MySQLiteHelper.getDatabase(getContext());
        long rowId = readableDatabase.insert(ProductsContract.TABLE_PRODUCT, null, values);

        if (rowId != -1){
            Log.d(TAG, "insertion in " + ProductsContract.TABLE_PRODUCT + " succeed");
        }

        return Uri.parse(ProductsContract.AUTHORITY_URI + "/" + ProductsContract.TABLE_PRODUCT + "/" + rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase readableDatabase = MySQLiteHelper.getDatabase(getContext());
        int res = readableDatabase.delete(ProductsContract.TABLE_PRODUCT, selection, selectionArgs);

        return res;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase database = MySQLiteHelper.getDatabase(getContext());
        int res = database.update(ProductsContract.TABLE_PRODUCT, values, selection, selectionArgs);

        return res;
    }
}
