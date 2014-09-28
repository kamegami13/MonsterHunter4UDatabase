package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/*
 *  Refer to MonsterListPagerAdapter and MonsterListFragment on 
 *  how to call this loader
 *  Note: Either type or location or both should be null when created
 */
public class HuntingFleetListCursorLoader extends SQLiteCursorLoader {
	private String type; // "Fishing", "Treasure","Hunting", or null
	private String location; // "Moga Coast", "Dark Waters", "Nearby Island",
								// "Moga Shallows",
								// "Moga Deep", "Legendary Tides",
								// "Moga Strait", "Abyssal Volcano",
								// "Pirate"s Tomb", "Distant
								// Isle", "No-man"s Land",
								// or null

	public HuntingFleetListCursorLoader(Context context, String type,
			String location) {
		super(context);
		this.type = type;
		this.location = location;
	}

	/*
	 * Note: Location gets priority, followed by type
	 */
	@Override
	protected Cursor loadCursor() {
		// Query the list of all hunting fleet data
		if (location != null) {
			return DataManager.get(getContext()).queryHuntingFleetLocation(
					location);
		} else if (type != null) {
			return DataManager.get(getContext()).queryHuntingFleetType(type);
		} else {
			return DataManager.get(getContext()).queryHuntingFleets();
		}
	}
}
