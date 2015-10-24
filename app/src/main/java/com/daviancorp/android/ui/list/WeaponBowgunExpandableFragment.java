package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.view.View;

import com.daviancorp.android.ui.adapter.WeaponExpandableListBowgunAdapter;
import com.daviancorp.android.ui.adapter.WeaponExpandableListGeneralAdapter;

/**
 * Created by Mark on 3/5/2015.
 */
public class WeaponBowgunExpandableFragment extends WeaponListFragment {

    public static WeaponBowgunExpandableFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(WeaponListFragment.ARG_TYPE, type);
        WeaponBowgunExpandableFragment f = new WeaponBowgunExpandableFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    protected WeaponExpandableListGeneralAdapter createWeaponAdapter() {
        return new WeaponExpandableListBowgunAdapter(getActivity(), new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = mRecyclerView.getChildPosition(v);
                mAdapter.toggleGroup(position);

                return true;
            }
        });
    }
}
