package com.jgl.tptravelapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper implements BookmarkManager{
    private static MySQLiteOpenHelper dbHelper;

    private static final String DATABASE_NAME = "bookmarkDB";
    private static final int DATABASE_VERSION = 1;

    private static final String BOOKMARK_TABLE_NAME = "bookmark";
    private static final String BOOKMARK_COLUMN_IDEA_TITLE = "idea_title";

    private static final String BOOKMARK_TABLE_CREATE =
            "CREATE TABLE " + BOOKMARK_TABLE_NAME + " (" +
                    BOOKMARK_COLUMN_IDEA_TITLE + " TEXT);";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BOOKMARK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //Ajouter ici les opérations SQL à exécuter lors d'un changement de version
    }

    public void toggleBookmark(String ideaTitle){
        if (isABookmark(ideaTitle)){
            unmarkAsBookmark(ideaTitle);
        }
        else if (!isABookmark(ideaTitle)){
            markAsBookmark(ideaTitle);
        }
    }

    public boolean isABookmark(String ideaTitle){
        boolean isABookMark = false;

        Cursor cursor = getReadableDatabase().query(BOOKMARK_TABLE_NAME, null, BOOKMARK_COLUMN_IDEA_TITLE + " = ?", new String[]{ideaTitle}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                isABookMark = cursor.getString(0).equals(ideaTitle);
            }
            while(cursor.moveToNext());
        }

        return isABookMark;
    }

    public void markAsBookmark(String ideaTitle){
        ContentValues cv = new ContentValues();
        cv.put(BOOKMARK_COLUMN_IDEA_TITLE, ideaTitle);

        getWritableDatabase().insert(BOOKMARK_TABLE_NAME, null, cv);
    }

    public void unmarkAsBookmark(String ideaTitle){
        getWritableDatabase().delete(BOOKMARK_TABLE_NAME, BOOKMARK_COLUMN_IDEA_TITLE + " = ?", new String[]{ideaTitle});
    }
}
