package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.daviancorp.android.data.classes.Rank;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ArmorExpandableListPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

import java.util.ArrayList;
import java.util.List;

public class ArmorListActivity extends GenericTabActivity {

    private ViewPager viewPager;
    private ArmorExpandableListPagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.armor);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ArmorExpandableListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

        // Enable back button if we're coming from the set builder
        if (getIntent().getBooleanExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, false)) {
            super.disableDrawerIndicator();
            if (getIntent().getIntExtra(ASBActivity.EXTRA_SET_HUNTER_TYPE, -1) == 1) {
                viewPager.setCurrentItem(1); // We change to the gunner page if its a gunner set
            }
        }
        else {
            super.enableDrawerIndicator();
        }

        // Tag as top level activity
        super.setAsTopLevel();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ARMOR;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
