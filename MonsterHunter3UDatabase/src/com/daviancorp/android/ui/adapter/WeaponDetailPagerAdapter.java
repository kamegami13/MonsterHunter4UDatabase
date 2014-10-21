package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.data.database.S;
import com.daviancorp.android.ui.detail.ComponentListFragment;
import com.daviancorp.android.ui.detail.WeaponBladeDetailFragment;
import com.daviancorp.android.ui.detail.WeaponBowDetailFragment;
import com.daviancorp.android.ui.detail.WeaponBowgunDetailFragment;
import com.daviancorp.android.ui.detail.WeaponTreeFragment;

public class WeaponDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long weaponId;

	public WeaponDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.weaponId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Weapon detail
			if (weaponId < S.SECTION_BOWGUN) {
				return WeaponBladeDetailFragment.newInstance(weaponId);
			}
			else if (weaponId < S.SECTION_BOW) {
				return WeaponBowgunDetailFragment.newInstance(weaponId);
			}
			else {
				return WeaponBowDetailFragment.newInstance(weaponId);
			}
		case 1:
			// Weapon tree
			return WeaponTreeFragment.newInstance(weaponId);
		case 2:
			// Weapon Components
			return ComponentListFragment.newInstance(weaponId);
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get weapon count - equal to number of tabs
		return 3;
	}

}