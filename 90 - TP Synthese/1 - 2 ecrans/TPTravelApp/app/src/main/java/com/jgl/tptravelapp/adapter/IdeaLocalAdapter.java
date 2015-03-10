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
public class IdeaLocalAdapter extends BaseAdapter {

    public static final String TAG = "IdeaLocalAdapter";

    protected Context context;
    protected List<Idea> ideas = new ArrayList<Idea>();

    public IdeaLocalAdapter(Context context) {
        this.context = context;
    }

    /**
     * Initialisation de l'adapter : récupération de la source de données
     */
    public void init(){
        ideas = Category.getCategory().getIdeas();
    }

    /**
     * Récupération du nombre de ligne total de la source de données
     */
    @Override
    public int getCount() {
        return ideas.size();
    }

    /**
     * Récupération de l'entité liée à l'adapter par position
     */
    @Override
    public Object getItem(int position) {
        return ideas.get(position);
    }

    /**
     * Récupération de l'identifiant de la ligne demandée (ici pas d'identifiant de type long : reprise de la position de l'élément dans la catégorie)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // Récupération d'un inflater : permet de générer les vues d'interface à partir du fichier xml
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // génération des vues d'interface du fichier layout/row_idea.xml - récupération de la vue racine
        ViewGroup rowView = (ViewGroup) inflater.inflate(R.layout.row_idea, null);

        // Récupération de références vers les vues à mettre à jour
        ImageView reviewImage = (ImageView) rowView.findViewById(R.id.ideaImage);
        TextView reviewTitle = (TextView) rowView.findViewById(R.id.ideaTitle);

        // Mise à jour des vues avec les données de l'entité (idée) demandée (position)
        Idea idea = ideas.get(position);
        reviewImage.setImageResource(idea.getIdeaDrawableId());
        reviewTitle.setText(idea.getIdeaName());

        // Retourne la vue correspondant à la ligne demandée (sera ensuite affiché par la liste)
        return rowView;
    }
}
