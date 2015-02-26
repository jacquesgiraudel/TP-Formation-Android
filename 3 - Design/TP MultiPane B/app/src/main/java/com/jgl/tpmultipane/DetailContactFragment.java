package com.jgl.tpmultipane;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailContactFragment extends Fragment {

    public static final String TAG = "DetailContactFragment";
    static final String[] PROJECTION = new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_URI};
    static final String SELECTION = ContactsContract.Contacts._ID + " = ?";
    private String selectedId;

    public DetailContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_detail_contact, container, false);

        if (savedInstanceState != null){
            selectedId = savedInstanceState.getString("selectedContact");
        }
        else if (savedInstanceState == null){
            Bundle extras = getActivity().getIntent().getExtras();
            if (extras != null) {
                selectedId = extras.getString(DetailContactActivity.EXTRA_CONTACT_ID);
            }

            Log.d(TAG, "id " + selectedId);
        }
        Log.d(TAG, "displaying " + selectedId);
        displayContact(root, selectedId);

        // Inflate the layout for this fragment
        return root;
    }

    public void displayContact(View root, String id){
        selectedId = id;
        ((ImageView) root.findViewById(R.id.DC_imageView_photo)).setImageBitmap(null);
        if (id != null) {
            Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, SELECTION, new String[]{id}, null);
            Log.d(TAG, "nb rows " + cursor.getCount());
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String photoUri = cursor.getString(2);

                ((TextView) root.findViewById(R.id.DC_textView_name)).setText(name);
                ImageView photoImageView = ((ImageView) root.findViewById(R.id.DC_imageView_photo));
                if (photoUri != null) {
                    photoImageView.setImageURI(Uri.parse(photoUri));
                }

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                boolean displayImage = prefs.getBoolean("displayImage", false);
                if (displayImage){
                    photoImageView.setVisibility(View.VISIBLE);
                }
                else if (!displayImage){
                    photoImageView.setVisibility(View.GONE);
                }
            }
            cursor.close();
        }
    }

    public void onSaveInstanceState(Bundle stateToFill) {
        stateToFill.putString("selectedContact", selectedId);
        Log.d(TAG, "saving " + selectedId);

        super.onSaveInstanceState(stateToFill);
    }

}
