package com.jgl.tplistview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class WebListViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_list_view);

        ListView listView = (ListView) findViewById(R.id.listView);
        WebAdapter webAdapter = new WebAdapter(this);
        webAdapter.init();
        listView.setAdapter(webAdapter);
    }

}
