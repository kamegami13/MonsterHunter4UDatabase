package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/*
 *  Refer to MonsterListPagerAdapter and MonsterListFragment on 
 *  how to call this loader
 */
public class WeaponTreeListCursorLoader extends SQLiteCursorLoader {
	private long id;

	public WeaponTreeListCursorLoader(Context context, long id) {
		super(context);
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of weapons in the tree
		return DataManager.get(getContext()).queryWeaponTree(id);

	}
}
