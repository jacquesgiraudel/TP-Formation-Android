package com.jgl.tpmultipane;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;


public class
        MultiPaneHostActivity extends ActionBarActivity implements ListContactsFragment.Callbacks, SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = "MultiPaneHostActivity";
    private boolean multiPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_pane_host);
        multiPane = (findViewById(R.id.LC_fragment_detailContact) != null);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_pane_host, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        boolean existsSettings = getFragmentManager().findFragmentByTag("settings") != null;
        if (id == R.id.action_settings && !existsSettings) {
            Log.d(TAG, "Opening settings");
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String id) {

        if (multiPane) {
            DetailContactFragment detailFragment = (DetailContactFragment) getFragmentManager().findFragmentById(R.id.LC_fragment_detailContact);
            detailFragment.displayContact(detailFragment.getView(), id);
        } else if (!multiPane) {
            Intent intent = new Intent(this, DetailContactActivity.class);
            intent.putExtra(DetailContactActivity.EXTRA_CONTACT_ID, id);
            startActivity(intent);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean displayImage = prefs.getBoolean("displayImage", false);

        if (key.compareTo("displayImage") == 0 && multiPane) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.LC_fragment_detailContact);
            ImageView photo = (ImageView) fragment.getView().findViewById(R.id.DC_imageView_photo);
            if (displayImage) {
                photo.setVisibility(View.VISIBLE);
            } else if (!displayImage) {
                photo.setVisibility(View.GONE);
            }
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Les préférences ont changées")
                        .setContentText("displayImage est passée à " + displayImage);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(new Random().nextInt(), mBuilder.build());
    }

    @Override
    protected void onDestroy() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

}
