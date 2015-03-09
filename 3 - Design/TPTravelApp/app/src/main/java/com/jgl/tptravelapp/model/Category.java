package com.jgl.tptravelapp.model;

import android.content.Context;

import com.jgl.tptravelapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacques Giraudel on 09/03/2015.
 */
public class Category {
    private String title;
    private List<Idea> ideas = new ArrayList<Idea>();

    public Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<Idea> ideas) {
        this.ideas = ideas;
    }

    public static Category getCategory(Context context){
        Category category = new Category("Catégorie d'idée A");
        category.getIdeas().add(new Idea("Hannover", R.drawable.hannover, context.getString(R.string.idea_description_default), 52.3758916d, 9.7320104d));
        category.getIdeas().add(new Idea("Hong Kong", R.drawable.hongkongskyline, context.getString(R.string.idea_description_default), 22.2577828d, 114.1995978d));

        return category;
    }
}
