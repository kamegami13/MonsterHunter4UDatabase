package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class LocationListCursorLoader extends SQLiteCursorLoader {

	public LocationListCursorLoader(Context context) {
		super(context);
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all locations
		return DataManager.get(getContext()).queryLocations();
	}
}
