package com.jgl.tpmultipane;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Ecran détail (utiliser uniquement en non multicadre)
 */
public class DetailContactActivity extends ActionBarActivity{

    public static final String EXTRA_CONTACT_ID = "contact_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
    }

}
