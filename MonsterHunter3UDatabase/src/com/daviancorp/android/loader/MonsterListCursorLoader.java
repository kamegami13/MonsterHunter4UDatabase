package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.daviancorp.android.data.database.DataManager;

/*
 * For constructor, pass in either "Small", "Large", or null
 */
public class MonsterListCursorLoader extends SQLiteCursorLoader {
	private String tab; // "Small", "Large", or null

	public MonsterListCursorLoader(Context context, String tab) {
		super(context);
		this.tab = tab;
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all monsters

		if (tab == null) {
			return DataManager.get(getContext()).queryMonsters();
		} else if (tab.equals("Small")) {
			Log.d("hoooooo", "SMALLS");
			return DataManager.get(getContext()).querySmallMonsters();
		} else if (tab.equals("Large")) {
			return DataManager.get(getContext()).queryLargeMonsters();
		} else {
			return DataManager.get(getContext()).queryMonsters();
		}
	}
}
