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
			// Great Sword
			return WeaponBladeListFragment.newInstance("Great Sword");
		case 1:
			// Long Sword
			return WeaponBladeListFragment.newInstance("Long Sword");
		case 2:
			// Sword and Shield
			return WeaponBladeListFragment.newInstance("Sword and Shield");
		case 3:
			// Dual Blades
			return WeaponBladeListFragment.newInstance("Dual Blades");
		case 4:
			// Hammer
			return WeaponBladeListFragment.newInstance("Hammer");
		case 5:
			// Hunting Horn 
			return WeaponBladeListFragment.newInstance("Hunting Horn");
		case 6:
			// Lance
			return WeaponBladeListFragment.newInstance("Lance");
		case 7:
			// Gunlance
			return WeaponBladeListFragment.newInstance("Gunlance");
		case 8:
			// Switch Axe
			return WeaponBladeListFragment.newInstance("Switch Axe");
		case 9:
			// Light Bowgun
			return WeaponBowgunListFragment.newInstance("Light Bowgun");
		case 10:
			// Heavy Bowgun
			return WeaponBowgunListFragment.newInstance("Heavy Bowgun");
		case 11:
			// Bow
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