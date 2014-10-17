package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/*
 * For constructor, pass in either "Small", "Large", or null
 */
public class MonsterListCursorLoader extends SQLiteCursorLoader {
	public static String TAB_SMALL = "Small";
	public static String TAB_LARGE = "Large";
	
	private String tab; 	// "Small", "Large", or null

	public MonsterListCursorLoader(Context context, String tab) {
		super(context);
		this.tab = tab;
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all monsters

		if (tab == null) {
			return DataManager.get(getContext()).queryMonsters();
		} else if (tab.equals(TAB_SMALL)) {
			return DataManager.get(getContext()).querySmallMonsters();
		} else if (tab.equals(TAB_LARGE)) {
			return DataManager.get(getContext()).queryLargeMonsters();
		} else {
			return DataManager.get(getContext()).queryMonsters();
		}
	}
}
