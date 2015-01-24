package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class CombiningListCursorLoader extends SQLiteCursorLoader {

	public CombiningListCursorLoader(Context context) {
		super(context);
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all combinings
		return DataManager.get(getContext()).queryCombinings();
	}
}
