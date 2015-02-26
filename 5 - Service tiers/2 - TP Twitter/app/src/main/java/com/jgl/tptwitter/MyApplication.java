package com.jgl.tptwitter;

import android.app.Application;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Jacques on 27/01/2015.
 */
public class MyApplication extends Application {

    public static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig =
                 new TwitterAuthConfig("I3ejVEDETWso50WmzDA5gjZwp",
                         "qrQepwvZGOlhXPNhw1GF1FAcFvJG0DzET7rJZHfjMphyM4qgvb");

        Fabric.with(this, new Twitter(authConfig));
    }
}
