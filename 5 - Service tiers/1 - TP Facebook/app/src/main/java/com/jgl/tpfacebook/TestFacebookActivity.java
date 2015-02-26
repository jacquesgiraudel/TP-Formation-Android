package com.jgl.tpfacebook;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.widget.LoginButton;

import java.util.Arrays;


public class TestFacebookActivity extends FragmentActivity {

    public static final String TAG = "TestFacebookActivity";

    private static final String PERMISSION = "publish_actions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_facebook);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        LoginButton loginButton = (LoginButton) findViewById(R.id.authButton);
        loginButton.setPublishPermissions(Arrays.asList(PERMISSION));
    }

    public void postStatus(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        final String statusText = editText.getText().toString();
        Session session = Session.getActiveSession();
        if (session != null) {

            if (session.isOpened() && hasPublishPermission()) {
                Request request = Request
                        .newStatusUpdateRequest(Session.getActiveSession(), statusText, null, null, new Request.Callback() {
                            @Override
                            public void onCompleted(Response response) {
                                showPublishResult(statusText, response.getGraphObject(), response.getError());
                            }
                        });
                request.executeAsync();
                return;
            }
            else {
                Toast.makeText(this, getString(R.string.err_notConnected), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, getString(R.string.err_notConnected), Toast.LENGTH_SHORT).show();
        }
    }

    private UiLifecycleHelper uiHelper;

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null && session.getPermissions().contains("publish_actions");
    }

    private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
        String title = null;
        String alertMessage = null;
        if (error == null) {
            title = getString(R.string.success);
            alertMessage = getString(R.string.successfully_posted_post, message);
        } else {
            title = getString(R.string.error);
            alertMessage = error.getErrorMessage();
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(alertMessage)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}
