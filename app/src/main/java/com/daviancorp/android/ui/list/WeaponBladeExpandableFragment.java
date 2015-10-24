package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.view.View;

import com.daviancorp.android.ui.adapter.WeaponExpandableListBladeAdapter;
import com.daviancorp.android.ui.adapter.WeaponExpandableListGeneralAdapter;

/**
 * Created by Mark on 3/3/2015.
 */
public class WeaponBladeExpandableFragment extends WeaponListFragment {

    public static WeaponBladeExpandableFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(WeaponListFragment.ARG_TYPE, type);
        WeaponBladeExpandableFragment f = new WeaponBladeExpandableFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    protected WeaponExpandableListGeneralAdapter createWeaponAdapter() {
        return new WeaponExpandableListBladeAdapter(getActivity(), new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = mRecyclerView.getChildPosition(v);
                mAdapter.toggleGroup(position);

                return true;
            }
         });
    }
}
