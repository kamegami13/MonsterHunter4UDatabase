package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.list.ArmorExpandableListFragment;

public class ArmorExpandableListPagerAdapter extends FragmentPagerAdapter {

	public ArmorExpandableListPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Armor list for both Blademaster and Gunner
			return ArmorExpandableListFragment.newInstance("Both");
		case 1:
			// Armor list for both Blademaster
			return ArmorExpandableListFragment.newInstance("Blade");
		case 2:
			// Armor list for both Gunner
			return ArmorExpandableListFragment.newInstance("Gunner");
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}