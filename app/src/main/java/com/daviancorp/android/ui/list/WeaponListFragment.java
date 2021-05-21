package com.daviancorp.android.ui.list;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.WeaponExpandableListGeneralAdapter;
import com.daviancorp.android.ui.general.WeaponListEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * The superclass for weapons list fragments of every weapon type.
 */
public abstract class WeaponListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<WeaponListEntry>> {

    protected static final String ARG_TYPE = "WEAPON_TYPE";
    private static final String GROUPS_KEY = "groups_key";

    private LinearLayoutManager mLayoutManager;
    private Bundle savedState;

    // todo: Use a better mechanism to filter (filter command / object)
    private boolean isFilterFinal = false;

    // todo: refactor so that these variables don't have to be set to protected
    protected WeaponExpandableListGeneralAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        savedState = savedInstanceState;

        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.weapon_list_fragment, getArguments(), this).forceLoad();
    }

    /**
     * Used by onCreateView to build an adapter that creates the actual views for all weapons.
     * @return
     */
    protected abstract WeaponExpandableListGeneralAdapter createWeaponAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weapon_tree_expandable, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.content_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = createWeaponAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // Restores old groups if we are returning to the fragment
        if (savedInstanceState != null) {
            List<Integer> groups = savedInstanceState.getIntegerArrayList(GROUPS_KEY);
            mAdapter.restoreGroups(groups);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_weapon, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_final:
                // toggle state
                item.setChecked(!item.isChecked());
                isFilterFinal = item.isChecked();

                // restart the loader so that filtering can occur
                getLoaderManager()
                        .restartLoader(R.id.weapon_list_fragment, getArguments(), this)
                        .forceLoad();

                return true;

            default:
                return false;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Restores old groups if we are returning to the fragment
        if (savedInstanceState != null) {
            List<Integer> groups = savedInstanceState.getIntegerArrayList(GROUPS_KEY);
            mAdapter.restoreGroups(groups);
        }
    }

    //
    // Saves current state of groups
    //
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(GROUPS_KEY, mAdapter.saveGroups());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeaponListEntry>> loader,
                               ArrayList<WeaponListEntry> weapons) {
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.clear();
        mAdapter.addAll(weapons);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeaponListEntry>> loader) {
        // Stop using the cursor (via the adapter)
        mRecyclerView.setAdapter(null);
        mAdapter.clear();
    }

    @Override
    public Loader<ArrayList<WeaponListEntry>> onCreateLoader(int id, Bundle args) {
        // You only ever load the runs, so assume this is the case
        String mType = null;
        if (args != null) {
            mType = args.getString(ARG_TYPE);
        }
        return new WeaponArrayLoader(getActivity().getApplicationContext(), mType, isFilterFinal);
    }

    static class WeaponArrayLoader extends AsyncTaskLoader<ArrayList<WeaponListEntry>> {
        String mType;
        boolean mFinalOnly;
        public WeaponArrayLoader(Context context, String type, boolean finalOnly) {
            super(context);
            mType = type;
            mFinalOnly = finalOnly;
        }

        public ArrayList<WeaponListEntry> loadInBackground() {
            if (mFinalOnly) {
                return DataManager.get(getContext()).queryWeaponTreeArrayFinal(mType);
            } else {
                return DataManager.get(getContext()).queryWeaponTreeArray(mType);
            }
        }
    }

    @Override
    public void onPause() {
        savedState = new Bundle();
        savedState.putIntegerArrayList(GROUPS_KEY, mAdapter.saveGroups());
        super.onPause();
    }

    @Override
    public void onResume() {
        if (savedState != null) {
            List<Integer> groups = savedState.getIntegerArrayList(GROUPS_KEY);
            mAdapter.restoreGroups(groups);
        }
        super.onResume();
    }
}
