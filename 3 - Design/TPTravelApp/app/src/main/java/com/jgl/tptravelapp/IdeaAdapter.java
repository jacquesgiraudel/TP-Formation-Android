package com.jgl.tptravelapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacques Giraudel on 07/03/2015.
 */
public class IdeaAdapter extends BaseAdapter {

    public static final String TAG = "ReviewAdapter";

    private Context context;
    private List<Idea> reviews = new ArrayList<Idea>();

    public IdeaAdapter(Context context) {
        this.context = context;
    }

    public void init(){
        reviews.add(new Idea("Hannover", R.drawable.hannover, context.getString(R.string.idea_description_default), 52.3758916d, 9.7320104d));
        reviews.add(new Idea("Hong Kong", R.drawable.hongkongskyline, context.getString(R.string.idea_description_default), 22.2577828d, 114.1995978d));
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
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
        reviewImage.setImageResource(reviews.get(i).getIdeaDrawableId());
        TextView reviewTitle = (TextView) rowView.findViewById(R.id.ideaTitle);
        reviewTitle.setText(reviews.get(i).getIdeaName());

        return rowView;
    }

}
