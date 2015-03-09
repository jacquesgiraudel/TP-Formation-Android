package com.jgl.tptravelapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgl.tptravelapp.model.Category;
import com.jgl.tptravelapp.model.Idea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacques Giraudel on 07/03/2015.
 */
public class IdeaAdapter extends BaseAdapter {

    public static final String TAG = "IdeaAdapter";

    private Context context;
    private List<Idea> ideas = new ArrayList<Idea>();

    public IdeaAdapter(Context context) {
        this.context = context;
    }

    public void init(){
        ideas = Category.getCategory(context).getIdeas();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return ideas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d(TAG, "position " + i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup rowView = (ViewGroup) inflater.inflate(R.layout.row_idea, null);
        ImageView reviewImage = (ImageView) rowView.findViewById(R.id.ideaImage);
        reviewImage.setImageResource(ideas.get(i).getIdeaDrawableId());
        TextView reviewTitle = (TextView) rowView.findViewById(R.id.ideaTitle);
        reviewTitle.setText(ideas.get(i).getIdeaName());

        return rowView;
    }

}
