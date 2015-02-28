package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.loader.MonsterListCursorLoader;
import com.daviancorp.android.ui.list.MonsterListFragment;

public class MonsterGridPagerAdapter extends FragmentPagerAdapter {

    // Tab titles
    private String[] tabs = {
            MonsterListCursorLoader.TAB_LARGE,
            MonsterListCursorLoader.TAB_SMALL,
            "All"
    };

	public MonsterGridPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
            // Monster grid (large monsters)
            return MonsterListFragment.newInstance("Large");
		case 1:
			// Monster grid (small monsters)
			return MonsterListFragment.newInstance("Small");
		case 2:
            // Monster grid (all monsters)
            return MonsterListFragment.newInstance(null);
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
		return 3;
	}

}