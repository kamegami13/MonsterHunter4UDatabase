package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Location;

public class LocationLoader extends DataLoader<Location> {
	private long mLocationId;
	
	public LocationLoader(Context context, long locationId) {
		super(context);
		mLocationId = locationId;
	}
	
	@Override
	public Location loadInBackground() {
		return DataManager.get(getContext()).getLocation(mLocationId);
	}
}
