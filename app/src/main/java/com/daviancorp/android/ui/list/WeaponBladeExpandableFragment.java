package com.daviancorp.android.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.WeaponExpandableListBladeAdapter;
import com.daviancorp.android.ui.general.WeaponListEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 3/3/2015.
 */
public class WeaponBladeExpandableFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<WeaponListEntry>> {

    protected static final String ARG_TYPE = "WEAPON_TYPE";

    private WeaponExpandableListBladeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private static final String GROUPS_KEY = "groups_key";
    private Bundle mSavedState;

    public static WeaponBladeExpandableFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(WeaponListFragment.ARG_TYPE, type);
        WeaponBladeExpandableFragment f = new WeaponBladeExpandableFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weapon_tree_expandable, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.content_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WeaponExpandableListBladeAdapter(getActivity(), new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = mRecyclerView.getChildPosition(v);
                mAdapter.toggleGroup(position);

                return true;
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mSavedState = savedInstanceState;

        //
        // Add content to adapter (Just need to change loader to maintain parent/child!)
        //

        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.weapon_list_fragment, getArguments(), this).forceLoad();

        return rootView;
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
        mAdapter.addAll(weapons);

        // Restores old groups if we are returning to the fragment
        if (mSavedState != null) {
            List<Integer> groups = mSavedState.getIntegerArrayList(GROUPS_KEY);
            mAdapter.restoreGroups(groups);
        }
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
        return new WeaponArrayLoader(getActivity().getApplicationContext(), mType);
    }

    static class WeaponArrayLoader extends AsyncTaskLoader<ArrayList<WeaponListEntry>> {
        String mType;
        public WeaponArrayLoader(Context context, String type) {
            super(context);
            mType = type;
        }

        public ArrayList<WeaponListEntry> loadInBackground() {
            return DataManager.get(getContext()).queryWeaponTreeArray(mType);
        }
    }
}
