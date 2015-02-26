package com.jgl.autocatalogueur;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    public static final String TAG = "MyReceiver";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG, "onReceive");
        intent.setClass(context.getApplicationContext(), AutocatalogueurService.class);
        context.startService(intent);
    }
}
