package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.loader.GatheringListCursorLoader;
import com.daviancorp.android.ui.detail.LocationDetailFragment;
import com.daviancorp.android.ui.detail.LocationHabitatFragment;
import com.daviancorp.android.ui.detail.LocationRankFragment;

public class LocationDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long locationId;

    // Tab titles
    private String[] tabs = { "Map" ,"Monsters", "Low Rank" , "High Rank" , "G Rank" };

	public LocationDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.locationId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Location detail
			return LocationDetailFragment.newInstance(locationId);
        case 1:
            // Habitat detail
            return LocationHabitatFragment.newInstance(locationId);
		case 2:
			// Low-rank items
			return LocationRankFragment.newInstance(locationId, GatheringListCursorLoader.RANK_LR);
		case 3:
			// High-rank items
			return LocationRankFragment.newInstance(locationId, GatheringListCursorLoader.RANK_HR);
		case 4:
			// G-rank items
			return LocationRankFragment.newInstance(locationId, GatheringListCursorLoader.RANK_G);
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
		return 5;
	}

}