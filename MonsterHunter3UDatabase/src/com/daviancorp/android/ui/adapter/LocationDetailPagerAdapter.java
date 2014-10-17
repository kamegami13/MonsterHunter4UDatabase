package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.loader.GatheringListCursorLoader;
import com.daviancorp.android.ui.detail.LocationDetailFragment;
import com.daviancorp.android.ui.detail.LocationRankFragment;

public class LocationDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long locationId;

	public LocationDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.locationId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return LocationDetailFragment.newInstance(locationId);
		case 1:
			return LocationRankFragment.newInstance(locationId, GatheringListCursorLoader.RANK_LR);
		case 2:
			return LocationRankFragment.newInstance(locationId, GatheringListCursorLoader.RANK_HR);
		case 3:
			return LocationRankFragment.newInstance(locationId, GatheringListCursorLoader.RANK_G);
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}