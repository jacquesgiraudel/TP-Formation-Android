package com.jgl.tpstorageoptions;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProductsContract implements BaseColumns{
    public static final String AUTHORITY = "com.jgl.tpstorageoptions";
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.jgl.tpstorageoptions");

    public static final String TABLE_PRODUCT = "product";
    public static final String COLUMN_PRODUCT_NAME = "product_name";

    public static final String PRODUCT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_PRODUCT;
    public static final String PRODUCT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TABLE_PRODUCT;
    public static final int TABLE_PRODUCT_LIST = 1;
    public static final int TABLE_PRODUCT_ID = 2;

}
