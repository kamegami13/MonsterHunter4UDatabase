package com.daviancorp.android.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.ItemTradeFragment;

public class WyporiumTradeDetailPagerAdapter extends FragmentPagerAdapter {

    private long tradeId;

    // Tab titles
    private String[] tabs = { "Details" };

    public WyporiumTradeDetailPagerAdapter(FragmentManager fm, long id) {
        super(fm);
        this.tradeId = id;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Trade details
                return ItemTradeFragment.newInstance(tradeId);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int index) {
        return tabs[index];
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 1;
    }

}