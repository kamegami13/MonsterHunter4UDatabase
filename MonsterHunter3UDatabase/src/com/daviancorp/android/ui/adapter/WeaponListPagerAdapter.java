package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.list.WeaponBladeListFragment;
import com.daviancorp.android.ui.list.WeaponBowListFragment;
import com.daviancorp.android.ui.list.WeaponBowgunListFragment;

public class WeaponListPagerAdapter extends FragmentPagerAdapter {

	public WeaponListPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		
		// "Great Sword", "Long Sword", "Sword and Shield",
		// "Dual Blades", "Hammer", "Hunting Horn", "Lance",
		// "Gunlance", "Switch Axe", "Light Bowgun",
		// "Heavy Bowgun", "Bow"

		switch (index) {
		case 0:
			return WeaponBladeListFragment.newInstance("Great Sword");
		case 1:
			return WeaponBladeListFragment.newInstance("Long Sword");
		case 2:
			return WeaponBladeListFragment.newInstance("Sword and Shield");
		case 3:
			return WeaponBladeListFragment.newInstance("Dual Blades");
		case 4:
			return WeaponBladeListFragment.newInstance("Hammer");
		case 5:
			return WeaponBladeListFragment.newInstance("Hunting Horn");
		case 6:
			return WeaponBladeListFragment.newInstance("Lance");
		case 7:
			return WeaponBladeListFragment.newInstance("Gunlance");
		case 8:
			return WeaponBladeListFragment.newInstance("Switch Axe");
		case 9:
			return WeaponBowgunListFragment.newInstance("Light Bowgun");
		case 10:
			return WeaponBowgunListFragment.newInstance("Heavy Bowgun");
		case 11:
			return WeaponBowListFragment.newInstance("Bow");
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 12;
	}

}