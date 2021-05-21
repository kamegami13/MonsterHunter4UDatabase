package com.daviancorp.android.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.list.ArmorExpandableListFragment;

public class ArmorExpandableListPagerAdapter extends FragmentPagerAdapter {

    // Tab titles
    private String[] tabs = { "Blade", "Gunner", "Both" };

	public ArmorExpandableListPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
            // Armor list for both Blademaster
            return ArmorExpandableListFragment.newInstance("Blade");
		case 1:
            // Armor list for both Gunner
            return ArmorExpandableListFragment.newInstance("Gunner");
		case 2:
            // Armor list for both Blademaster and Gunner
            return ArmorExpandableListFragment.newInstance("Both");
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