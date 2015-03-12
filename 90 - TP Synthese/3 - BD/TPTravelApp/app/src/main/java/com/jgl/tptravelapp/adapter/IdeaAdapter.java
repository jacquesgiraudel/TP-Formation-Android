package com.jgl.tptravelapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.internal.id;
import com.jgl.tptravelapp.R;
import com.jgl.tptravelapp.model.BookmarkManager;
import com.jgl.tptravelapp.model.Idea;
import com.jgl.tptravelapp.model.MySQLiteOpenHelper;
import com.jgl.tptravelapp.model.SPBookmarkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter pour la ListView de l'écran principal (liste des idées)
 * Rôle : requéter la source de données liée et créer les vues lignes pour la ListView
 */

public abstract class IdeaAdapter extends BaseAdapter {

    public static final String TAG = "IdeaAdapter";

    protected Context context;
    protected List<Idea> ideas = new ArrayList<Idea>();

    public IdeaAdapter(Context context) {
        this.context = context;
    }

    /**
     * Initialisation de l'adapter : récupération de la source de données
     */
    abstract public void init();

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
        ImageView bookmarkImage = (ImageView) rowView.findViewById(R.id.bookmarkImage);

        // Mise à jour des vues avec les données de l'entité (idée) demandée (position)
        Idea idea = ideas.get(position);
        reviewImage.setImageResource(idea.getIdeaDrawableId());
        reviewTitle.setText(idea.getIdeaName());

        initBookmarkState(bookmarkImage, ideas.get(position).getIdeaName());

        // Retourne la vue correspondant à la ligne demandée (sera ensuite affiché par la liste)
        return rowView;
    }

    /**
     * Mise à jour de l'état de l'indicateur de favori
     */
    private void initBookmarkState(ImageView bookmarkImage, String name){
        BookmarkManager bookmarkManager = new SPBookmarkManager(context);

        if (bookmarkManager.isABookmark(name)) {
            bookmarkImage.setImageResource(R.drawable.ic_heart);
        }
        else if (!bookmarkManager.isABookmark(name)) {
            bookmarkImage.setImageResource(R.drawable.ic_heart_outline);
        }
    }
}
