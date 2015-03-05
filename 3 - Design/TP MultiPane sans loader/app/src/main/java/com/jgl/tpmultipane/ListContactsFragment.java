package com.jgl.tpmultipane;


import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListContactsFragment extends ListFragment{
    public static final String TAG = "ListContactsFragment";
    public interface Callbacks{
        public void onItemSelected(String id);
    }

    private SimpleCursorAdapter mAdapter;
    static final String[] PROJECTION = new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
    private Callbacks mCallbacks;
    private Cursor mCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] fromColumns = {ContactsContract.Contacts.DISPLAY_NAME}; // Map colonnes et vues pour l’adapteur de curseur
        int[] toViews = {android.R.id.text1}; // La TextView dans simple_list_item_1
        mCursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                PROJECTION, null, null, null);
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, mCursor, fromColumns, toViews, 0);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        Log.d(TAG, "position " + (position - l.getFirstVisiblePosition()));
        if (mAdapter.getCursor().moveToPosition(position - l.getFirstVisiblePosition())){
            Log.d(TAG, "id " + mAdapter.getCursor().getString(0));
            mCallbacks.onItemSelected(mAdapter.getCursor().getString(0));
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        mCursor.close();
        super.onDetach();
    }

}
