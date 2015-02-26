package com.jgl.tptwitter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;


public class TweetActivity extends ActionBarActivity {

    public static final String TAG = "TweetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
    }

    public void updateStatus(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        TwitterApiClient apiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = apiClient.getStatusesService();
        statusesService.update(editText.getText().toString(), 1l, false, 0d, 0d, "", false, false, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> tweetResult) {
                Log.d(TAG, "posté");
                Toast.makeText(TweetActivity.this, "Posté", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException e) {
                Log.d(TAG, "Erreur");
                Toast.makeText(TweetActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
