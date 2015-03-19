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


/**
 * Ecran utilisant la vue primaire "onglets fixes" avec 2 onglets
 * L'implémentation de l'interface ActionBar.TabListener permet de répondre au clic sur onglet
 */
public class TabActivity extends ActionBarActivity implements ActionBar.TabListener {

    // Adapter fonctionnant avec le ViewPager, utilisation d'un fragment par page
    SectionsPagerAdapter mSectionsPagerAdapter;

    // ViewPager : vue permettant de réaliser les vues de swipe / glisser
    ViewPager mViewPager;
    // Mode action de la barre d'action
    ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        // Passage en navigation par onglets fixes
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Création de l'adapter des pages
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Assignation de l'adapter au ViewPager
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Définition d'un listener sur les changements de page (listener : objet détectant un comportement spécifique et définissant 1 ou des méthodes de traitement)
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // Mise à jour de l'onglet actif à partir de la page atteinte
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // Création des onglets et ajout à la barre d'action (et définition d'un listener sur changement d'onglet)
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
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

    /**
     * Méthode du listener de clic sur un onglet (la classe implémente ActionBar.TabListener)
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // Mise à jour de la page à partir de l'onglet sélectionné
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
     * Adapter du ViewPager, retourne un fragment par page
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Appelé pour instancier le fragment pour la page demandée
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Nombre de pages
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
     * Un fragment liste simple
     */
    public static class PlaceholderFragment extends ListFragment {
        /**
         * numéro de section/page
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Retourne une instance de fragment liste pour la page/section demandée
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

            // Récupération de la liste générée par ListFragment
            View view = super.onCreateView(inflater, container, savedInstanceState);

            // Création de l'adapter qui alimentera la liste
            ListAdapter adapter = null;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                // Adapter simple basé sur un tableau de String (1 ligne dans la liste par String)
                adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, new String[]{"Red", "Green", "Blue", "Black", "Grey"});
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                // Requête sur le ContentProvider Contact pour obtenir un curseur de données qui alimentera notre adapter
                Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts._ID, ContactsContract.Data.DISPLAY_NAME}, null, null, null);

                String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
                int[] toViews = {android.R.id.text1};

                // Adapter basé sur un curseur (1 ligne dans la liste par ligne de donnée)
                adapter = new SimpleCursorAdapter(getActivity(),
                        android.R.layout.simple_list_item_1, cursor, fromColumns, toViews, 0);
            }
            setListAdapter(adapter);



            return view;
        }

        /**
         * Appelé quand l'activité est créée (et l'écran affiché)
         */
        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Permet des sélections multiples sur la liste
            getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

            // Listener sur les événements liés au mode sélection multiple
            final ActionMode.Callback callback = new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    // Possibilité d'action sur changement d'état de sélection
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

            // Assignation du listener créé
            getListView().setMultiChoiceModeListener((AbsListView.MultiChoiceModeListener) callback);

            // Création et assignation d'un listener répondant à un long presser
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
