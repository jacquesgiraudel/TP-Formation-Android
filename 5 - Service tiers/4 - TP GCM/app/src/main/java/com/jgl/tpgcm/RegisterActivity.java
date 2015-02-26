package com.jgl.tpgcm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Enregistrement de l'appareil
        GcmRegistrationAsyncTask task = new GcmRegistrationAsyncTask(this);
        task.execute();
    }

}
