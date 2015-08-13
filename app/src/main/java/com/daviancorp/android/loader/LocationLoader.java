package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.classes.Location;
import com.daviancorp.android.data.database.DataManager;

public class LocationLoader extends DataLoader<Location> {
    private long mLocationId;

    public LocationLoader(Context context, long locationId) {
        super(context);
        mLocationId = locationId;
    }

    @Override
    public Location loadInBackground() {
        // Query the specific location
        return DataManager.get(getContext()).getLocation(mLocationId);
    }
}
