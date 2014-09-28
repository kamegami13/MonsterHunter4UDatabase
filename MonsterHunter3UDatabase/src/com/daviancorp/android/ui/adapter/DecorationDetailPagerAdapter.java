package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.ComponentListFragment;
import com.daviancorp.android.ui.detail.DecorationDetailFragment;
import com.daviancorp.android.ui.detail.ItemToSkillFragment;

public class DecorationDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long decorationId;

	public DecorationDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.decorationId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return DecorationDetailFragment.newInstance(decorationId);
		case 1:
			return ItemToSkillFragment.newInstance(decorationId, "Decoration");
		case 2:
			return ComponentListFragment.newInstance(decorationId);
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