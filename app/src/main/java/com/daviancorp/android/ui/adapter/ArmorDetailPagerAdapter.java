package com.daviancorp.android.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.ArmorDetailFragment;
import com.daviancorp.android.ui.detail.ComponentListFragment;
import com.daviancorp.android.ui.detail.ItemToSkillFragment;

public class ArmorDetailPagerAdapter extends FragmentPagerAdapter {

    // Tab titles
    private String[] tabs = { "Detail", "Skills", "Components"};
	
	private long armorId;

	public ArmorDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.armorId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Armor detail
			return ArmorDetailFragment.newInstance(armorId);
		case 1:
			// Armor skills
			return ItemToSkillFragment.newInstance(armorId, "Armor");
		case 2:
			// Item components to make Armor
			return ComponentListFragment.newInstance(armorId);
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