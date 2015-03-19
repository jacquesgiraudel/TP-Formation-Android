package com.jgl.tptravelapp.model;

import android.content.Context;

/**
 * Interface définissant les méthodes métier associées à la gestion des favoris
 */
public interface BookmarkManager {
    public void toggleBookmark(String ideaTitle);
    public boolean isABookmark(String ideaTitle);
    public void markAsBookmark(String ideaTitle);
    public void unmarkAsBookmark(String ideaTitle);
}
