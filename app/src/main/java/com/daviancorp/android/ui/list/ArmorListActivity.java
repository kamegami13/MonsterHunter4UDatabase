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

    private static final int LOW_RANK_RARITY_MIN = 1;
    private static final int LOW_RANK_RARITY_MAX = 3;

    private ViewPager viewPager;
    private ArmorExpandableListPagerAdapter mAdapter;

    private List<OnRarityLimitsChangedListener> rarityLimitsChangedListeners;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.armor);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ArmorExpandableListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

        rarityLimitsChangedListeners = new ArrayList<>();

        // Enable drawer button instead of back button
        super.enableDrawerIndicator();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ARMOR;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void addOnRarityLimitsChangedListener(OnRarityLimitsChangedListener rarityLimitsChangedListener) {
        this.rarityLimitsChangedListeners.add(rarityLimitsChangedListener);
    }

    public void notifyRarityLimitsChangedListeners(ArmorExpandableListFragment.ArmorFilter filter) {
        for (OnRarityLimitsChangedListener l : rarityLimitsChangedListeners) {
            l.onRarityLimitsChanged(filter);
        }
    }

    public static interface OnRarityLimitsChangedListener {
        public void onRarityLimitsChanged(ArmorExpandableListFragment.ArmorFilter filter);
    }
}
