package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/*
 *  Refer to MonsterListPagerAdapter and MonsterListFragment on 
 *  how to call this loader
 */
public class WeaponListCursorLoader extends SQLiteCursorLoader {
	private String type; // "Great Sword", "Long Sword", "Sword and Shield",
						 // "Dual Blades", "Hammer", "Hunting Horn", "Lance",
						 // "Gunlance", "Switch Axe", "Light Bowgun",
						 // "Heavy Bowgun", "Bow"

	public WeaponListCursorLoader(Context context, String type) {
		super(context);
		this.type = type;
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of weapons
		if (type == null) {
			return DataManager.get(getContext()).queryWeapon();
		} else {
			return DataManager.get(getContext()).queryWeaponType(type);
		}
	}
}
