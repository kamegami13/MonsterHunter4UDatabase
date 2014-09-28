package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.list.QuestExpandableListFragment;

public class QuestExpandableListPagerAdapter extends FragmentPagerAdapter {

	public QuestExpandableListPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return QuestExpandableListFragment.newInstance("Village");
		case 1:
			return QuestExpandableListFragment.newInstance("Port");
		case 2:
			return QuestExpandableListFragment.newInstance("DLC");
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