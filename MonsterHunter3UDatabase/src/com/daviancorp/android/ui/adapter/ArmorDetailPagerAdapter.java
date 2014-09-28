package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.ArmorDetailFragment;
import com.daviancorp.android.ui.detail.ComponentListFragment;
import com.daviancorp.android.ui.detail.ItemToSkillFragment;

public class ArmorDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long armorId;

	public ArmorDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.armorId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return ArmorDetailFragment.newInstance(armorId);
		case 1:
			return ItemToSkillFragment.newInstance(armorId, "Armor");
		case 2:
			return ComponentListFragment.newInstance(armorId);
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