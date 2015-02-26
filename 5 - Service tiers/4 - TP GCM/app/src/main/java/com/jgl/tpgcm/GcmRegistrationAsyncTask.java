package com.jgl.tpgcm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.jacques.myapplication.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class GcmRegistrationAsyncTask extends AsyncTask<Void, Void, String> {
    public static final String TAG = "GcmRegistrationAsyncTask";

    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;

    // Numéro de projet dans la console Google développeur
    private static final String SENDER_ID = "607186665361";

    public GcmRegistrationAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        // Peut provoquer une erreur PHONE_REGISTRATION_ERROR sur certaines versions d'émulateur (dont x86) car C2dm n'est pas activé
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Besoin de setRootUrl et setGoogleClientRequestInitializer uniquement pour du test en local
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // Fin du code optionnel

            regService = builder.build();
        }

        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            // Obtention d'un identifiant d'enregistrement
            String regId = gcm.register(SENDER_ID);
            msg = "Appareil enregistré, id d'enregistrement=" + regId;

            // Enregistrement de l'appareil
            regService.register(regId).execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Erreur: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Log.d(TAG, msg);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}