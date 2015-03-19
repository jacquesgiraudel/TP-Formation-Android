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
 * Fragment listant les contacts de l'appareil (ListFragment donne accès à un fragment lié à une ListView -> pas de onCreateView ni de inflate)
 */
public class ListContactsFragment extends ListFragment{
    public static final String TAG = "ListContactsFragment";

    /**
     * Callback : permet de découpler l'appel au parent (plutôt que de passer par un getActivity() et un cast)
     */
    public interface Callbacks{
        public void onItemSelected(String id);
    }

    private SimpleCursorAdapter mAdapter;
    // Colonnes à récupérer sur le ContentProvider Contact
    static final String[] PROJECTION = new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
    private Callbacks mCallbacks;
    private Cursor mCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] fromColumns = {ContactsContract.Contacts.DISPLAY_NAME}; // Map colonnes et vues pour l’adapteur de curseur
        int[] toViews = {android.R.id.text1}; // La TextView dans simple_list_item_1
        // Requête sur le ContentProvider Contact pour obtenir un curseur sur les données
        mCursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                PROJECTION, null, null, null);
        // Création d'un adapter basé sur cette requête
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, mCursor, fromColumns, toViews, 0);

        // Assignation de l'adapter à la liste du fragment
        setListAdapter(mAdapter);
    }

    /**
     * Méthode appelé lors d'un clic sur un élément de la liste
     */
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
