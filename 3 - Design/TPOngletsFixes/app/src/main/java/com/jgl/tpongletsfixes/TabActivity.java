package com.jgl.tpongletsfixes;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




public class TabActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public ActionMode getActionMode() {
        return mActionMode;
    }

    public void setActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends ListFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = super.onCreateView(inflater, container, savedInstanceState);
            ListAdapter adapter = null;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, new String[]{"Red", "Green", "Blue", "Black", "Grey"});
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts._ID, ContactsContract.Data.DISPLAY_NAME}, null, null, null);

                String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
                int[] toViews = {android.R.id.text1};
                adapter = new SimpleCursorAdapter(getActivity(),
                        android.R.layout.simple_list_item_1, cursor, fromColumns, toViews, 0);
            }
            setListAdapter(adapter);



            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

            final ActionMode.Callback callback = new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    // possibilité d'action sur changement d'état de sélection
                }
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    // Répond au clic sur les actions du menu
                    switch (item.getItemId()) {
                        case R.id.action_delete:
                            SparseBooleanArray selected = getListView().getCheckedItemPositions();
                            String elementsToDelete = "";
                            for (int i = 0; i < getListView().getCount(); i++) {
                                if (selected.get(i)) {
                                    elementsToDelete += i + ", ";
                                }
                            }
                            Toast.makeText(getActivity(), "Elements to delete : " + elementsToDelete, Toast.LENGTH_SHORT).show();
                            // Traitement de suppression des lignes à faire
                            //TODO - La fermeture ne fonctionne pas
                            ActionMode actionMode = ((TabActivity)getActivity()).getActionMode();
                            if (actionMode != null) {
                                actionMode.finish(); // Action effectuée, fermeture du menu
                            }
                            return true;
                        default:
                            return false;
                    }

                }
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    // Génération du menu
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.menu_items, menu);
                    return true;
                }
                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    // MAJ sur sorties du menu contextuel
                    ((TabActivity)getActivity()).setActionMode(null);

                }
                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    // MAJ du menu sur requête invalidate()
                    return false;
                }
            };

            getListView().setMultiChoiceModeListener((AbsListView.MultiChoiceModeListener) callback);

            getListView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ActionMode actionMode = ((TabActivity)getActivity()).getActionMode();
                    if (actionMode != null) {
                        return false;
                    }

                    // Démarre le menu d'action contextuel en utilisant le callback défini précédemment
                    actionMode = getActivity().startActionMode(callback);
                    ((TabActivity)getActivity()).setActionMode(actionMode);
                    view.setSelected(true);
                    return true;
                }
            });
        }
    }

}
