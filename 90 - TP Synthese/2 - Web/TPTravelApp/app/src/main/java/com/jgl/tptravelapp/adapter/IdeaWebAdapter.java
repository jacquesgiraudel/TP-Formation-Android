package com.jgl.tptravelapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jgl.tptravelapp.R;
import com.jgl.tptravelapp.model.Category;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Adapter d'idées se basant sur des données récupérées sur le web
 */
public class IdeaWebAdapter extends IdeaAdapter {

    public static final String TAG = "IdeaWebAdapter";

    public IdeaWebAdapter(Context context) {
        super(context);
    }

    @Override
    public void init() {
        loadIdeas();
    }

    /**
     * Redéfinition de getView pour lancer la tâche de récupération de l'image sur le web
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = super.getView(position, view, viewGroup);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.ideaImage);
        loadImage(imageView, position);

        return rowView;
    }

    /**
     * Lance le chargement des données si accès internet
     */
    private void loadIdeas() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoadIdeasTask task = new LoadIdeasTask();
            task.execute();
        }
    }

    /**
     * Tâche de chargement des données depuis un web service
     * AsyncTask permet de d'exécuter du code dans un thread différent
     * (impossible d'effectuer des requêtes web dans le thread par défaut car bloquerait/ralentirait potentiellement les MAJ de l'interface)
     */
    class LoadIdeasTask extends AsyncTask {

        private static final int PAGE_SIZE = 10;

        @Override
        protected Object doInBackground(Object[] objects) {
            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL("http://46.105.17.198/TPTravelApp/rest/Ideas?idx=" + ideas.size() + "&page_size=" + PAGE_SIZE);
                Log.d(TAG, url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect(); // Lance la requête

                if (conn.getResponseCode() == 200) { // Un status code de 200 indique OK
                    InputStream content = conn.getInputStream(); // Flux sur les données
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    JSONObject json = new JSONObject(builder.toString());
                    Gson gson = new Gson();
                    Category category = gson.fromJson(json.toString(), Category.class);
                    ideas.addAll(category.getIdeas());

                } else {
                    Log.e(TAG, "La requête a retourné l'erreur " + conn.getResponseCode());
                }
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de la requête", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            notifyDataSetChanged();
        }
    }

    private void loadImage(ImageView imageView, int position) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoadImageTask loadImageTask = new LoadImageTask(imageView);
            loadImageTask.execute(ideas.get(position).getIdeaDrawableUrl());
        }
    }

    public static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String[] params) {
            Bitmap bitmap = null;

            StringBuilder builder = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                Log.d(TAG, url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect(); // Lance la requête

                if (conn.getResponseCode() == 200) { // Un status code de 200 indique OK
                    InputStream content = conn.getInputStream(); // Flux sur les données
                    bitmap = BitmapFactory.decodeStream(content);
                } else {
                    Log.e(TAG, "La requête a retourné l'erreur " + conn.getResponseCode());
                }
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de la requête", e);
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
