package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.list.HuntingFleetListFragment;

public class HuntingFleetListPagerAdapter extends FragmentPagerAdapter {

	public HuntingFleetListPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return HuntingFleetListFragment.newInstance("Fishing", null);
		case 1:
			return HuntingFleetListFragment.newInstance("Treasure", null);
		case 2:
			return HuntingFleetListFragment.newInstance("Hunting", null);
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