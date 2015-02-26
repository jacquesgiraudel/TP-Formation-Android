package com.jgl.tplistview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;


public class WebAdapter extends BaseAdapter {

    public static final String TAG = "WebAdapter";
    private Context context;
    private List<Product> products = new ArrayList<Product>();
    private boolean allProductsObtained = false;

    public WebAdapter(Context context) {
        this.context = context;
    }

    public void init(){
        LoadProductsTask task = new LoadProductsTask();
        task.execute();
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return Long.valueOf(products.get(i).getProductId());
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View rowLayout = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            rowLayout = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        else if (convertView != null) {
            rowLayout = convertView;
        }

        TextView textView = (TextView) rowLayout.findViewById(android.R.id.text1);
        String productName = products.get(i).getProductName();
        textView.setText(productName);

        if(!allProductsObtained && i == products.size() - 1){
            loadProducts();
        }

        return rowLayout;
    }

    private void loadProducts(){
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoadProductsTask task = new LoadProductsTask();
            task.execute();
        }
    }

    class LoadProductsTask extends AsyncTask{

        private static final int PAGE_SIZE = 10;

        @Override
        protected Object doInBackground(Object[] objects) {
            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL("http://46.105.17.198/TPListView/rest/Products?idx=" + products.size() + "&page_size=" + PAGE_SIZE);
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
                    Products newProducts = gson.fromJson(json.toString(), Products.class);
                    if (newProducts.getItems().size() != 0) {
                        products.addAll(newProducts.getItems());
                        if(newProducts.getItems().size() % PAGE_SIZE != 0){
                            allProductsObtained = true;
                        }
                    }
                    else {
                        allProductsObtained = true;
                    }
                } else {
                    Log.e(TAG, "La requête a retourné l'erreur " + conn.getResponseCode());
                }
            }
            catch (Exception e){
                Log.e(TAG, "Erreur lors de la requête", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            notifyDataSetChanged();
        }
    }

    private class Products {

        private List<Product> products;

        public	List<Product> getItems() {
            return products;
        }

    }

    private class Product{
        private String productId;
        private String productName;

        Product(String productId, String productName) {
            this.productId = productId;
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }
    }

}
