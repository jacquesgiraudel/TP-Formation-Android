package com.jgl.tpproductsreader;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ReadProductsActivity extends ActionBarActivity {

    private static final Uri PRODUCTS_URI = Uri.parse("content://com.jgl.tpstorageoptions/product");
    private static final String PRODUCT_COLUMN_NAME = "product_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_products);

        TextView textView = (TextView) findViewById(R.id.RP_textView);

        String products = "";
        Cursor cursor = getContentResolver().query(PRODUCTS_URI, new String[]{PRODUCT_COLUMN_NAME}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                products += cursor.getString(0) + ", ";
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        textView.setText(products);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
