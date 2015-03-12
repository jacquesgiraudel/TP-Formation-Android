package com.jgl.tptravelapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Jacques Giraudel on 12/03/2015.
 */
public class SPBookmarkManager implements BookmarkManager {

    private Context context;
    private SharedPreferences prefs;

    public SPBookmarkManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences("BookmarkPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void toggleBookmark(String ideaTitle) {
        Set<String> bookmarks = prefs.getStringSet("bookmarks", new TreeSet<String>(){});
        if (bookmarks.contains(ideaTitle)){
            bookmarks.remove(ideaTitle);
        }
        else if (!bookmarks.contains(ideaTitle)){
            bookmarks.add(ideaTitle);
        }
        prefs.edit().putStringSet("bookmarks", bookmarks).apply();
    }

    @Override
    public boolean isABookmark(String ideaTitle) {
        Set<String> bookmarks = prefs.getStringSet("bookmarks", new TreeSet<String>(){});
        return bookmarks.contains(ideaTitle);
    }

    @Override
    public void markAsBookmark(String ideaTitle) {
        Set<String> bookmarks = prefs.getStringSet("bookmarks", new TreeSet<String>(){});
        bookmarks.add(ideaTitle);
        prefs.edit().putStringSet("bookmarks", bookmarks).apply();
    }

    @Override
    public void unmarkAsBookmark(String ideaTitle) {
        Set<String> bookmarks = prefs.getStringSet("bookmarks", new TreeSet<String>(){});
        bookmarks.remove(ideaTitle);
        prefs.edit().putStringSet("bookmarks", bookmarks).apply();
    }
}
