package com.jgl.autocatalogueur;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * BroadcastReceiver de notre application : détecte les prises de photos (branchement effectué dans le fichier manifeste) et aiguille sur le service de catalogage
 */
public class MyReceiver extends BroadcastReceiver {

    public static final String TAG = "MyReceiver";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        // Récupération de l'intent de prise de photo contenant l'uri de la photo prise
        // Changement de cible de l'intent, assignation de la classe du composant service
        intent.setClass(context.getApplicationContext(), AutocatalogueurService.class);
        // Lancement du composant service
        context.startService(intent);
    }
}
