package com.daviancorp.android.ui.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.ui.detail.ComponentListFragment;
import com.daviancorp.android.ui.detail.WeaponBladeDetailFragment;
import com.daviancorp.android.ui.detail.WeaponBowDetailFragment;
import com.daviancorp.android.ui.detail.WeaponBowgunDetailFragment;
import com.daviancorp.android.ui.detail.WeaponTreeFragment;

public class WeaponDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long weaponId;
    private Context mcontext;

    // Tab titles
    private String[] tabs = { "Detail", "Family Tree", "Components"};

	public WeaponDetailPagerAdapter(FragmentManager fm, Context context, long id) {
		super(fm);
		this.weaponId = id;
        this.mcontext = context;
	}

	@Override
	public Fragment getItem(int index) {

        String wtype = DataManager.get(mcontext).getWeapon(weaponId).getWtype();

		switch (index) {
		case 0:
            switch(wtype){
                case "Light Bowgun":
                case "Heavy Bowgun":
                    return WeaponBowgunDetailFragment.newInstance(weaponId);
                case "Bow":
                    return WeaponBowDetailFragment.newInstance(weaponId);
                default:
                    return WeaponBladeDetailFragment.newInstance(weaponId);
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
    public CharSequence getPageTitle(int index) {
        return tabs[index];
    }

	@Override
	public int getCount() {
		// get weapon count - equal to number of tabs
		return 3;
	}

}