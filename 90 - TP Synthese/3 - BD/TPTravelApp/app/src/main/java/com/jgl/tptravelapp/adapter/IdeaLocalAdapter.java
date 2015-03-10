package com.jgl.tptravelapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgl.tptravelapp.R;
import com.jgl.tptravelapp.model.Category;
import com.jgl.tptravelapp.model.Idea;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter d'idées se basant sur un jeu de données créé en local
 */
public class IdeaLocalAdapter extends IdeaAdapter {

    public static final String TAG = "IdeaLocalAdapter";

    public IdeaLocalAdapter(Context context) {
        super(context);
    }

    public void init(){
        ideas = Category.getCategory().getIdeas();
    }

}
