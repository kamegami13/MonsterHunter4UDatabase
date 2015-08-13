package com.daviancorp.android.ui.list;

import java.util.ArrayList;
import java.util.List;

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

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.WeaponExpandableListBowgunAdapter;
import com.daviancorp.android.ui.general.WeaponListEntry;

public class WeaponBowgunExpandableFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<WeaponListEntry>> {

    protected static final String ARG_TYPE = "WEAPON_TYPE";
    private static final String GROUPS_KEY = "groups_key";
    private WeaponExpandableListBowgunAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Bundle savedState;

    public static WeaponBowgunExpandableFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(WeaponListFragment.ARG_TYPE, type);
        WeaponBowgunExpandableFragment f = new WeaponBowgunExpandableFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        savedState = savedInstanceState;

        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.weapon_list_fragment, getArguments(), this).forceLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weapon_tree_expandable, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.content_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WeaponExpandableListBowgunAdapter(getActivity(), new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = mRecyclerView.getChildPosition(v);
                mAdapter.toggleGroup(position);

                return true;
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // Restores old groups if we are returning to the fragment
        if (savedInstanceState != null) {
            List<Integer> groups = savedInstanceState.getIntegerArrayList(GROUPS_KEY);
            mAdapter.restoreGroups(groups);
        }

        return rootView;
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
        return new WeaponArrayLoader(getActivity().getApplicationContext(), mType);
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
