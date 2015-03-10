package com.jgl.tptravelapp.model;

import android.content.Context;

import com.jgl.tptravelapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité Catégorie
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

    /**
     * Création d'un jeu de données catégorie et idées
     */
    public static Category getCategory(){
        String dummyDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";

        Category category = new Category("Catégorie d'idée A");
        category.getIdeas().add(new Idea("Hannover", R.drawable.hannover, dummyDescription, 52.3758916d, 9.7320104d));
        category.getIdeas().add(new Idea("Hong Kong", R.drawable.hongkongskyline, dummyDescription, 22.2577828d, 114.1995978d));

        return category;
    }
}
