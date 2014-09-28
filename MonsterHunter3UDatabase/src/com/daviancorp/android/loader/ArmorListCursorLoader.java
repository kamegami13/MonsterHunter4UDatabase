package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/*
 *  Refer to MonsterListPagerAdapter and MonsterListFragment on 
 *  how to call this loader
 */
public class ArmorListCursorLoader extends SQLiteCursorLoader {
	private String type; // "Both" (All), "Blade", or "Gunner"
	private String slot; // "Head", "Body", "Arms", "Waist", "Legs", or null
							// (All)

	public ArmorListCursorLoader(Context context, String type, String slot) {
		super(context);
		this.type = type;
		this.slot = slot;
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all armor
		if ((type == null) || (type.equals("Both"))) {
			if (slot == null) {
				return DataManager.get(getContext()).queryArmor();
			} else {
				return DataManager.get(getContext()).queryArmorSlot(slot);
			}
		} else {
			if (slot == null) {
				return DataManager.get(getContext()).queryArmorType(type);
			} else {
				return DataManager.get(getContext()).queryArmorTypeSlot(type,
						slot);
			}
		}
	}
}
