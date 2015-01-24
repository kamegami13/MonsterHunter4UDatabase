package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class DecorationListCursorLoader extends SQLiteCursorLoader {

	public DecorationListCursorLoader(Context context) {
		super(context);
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all decorations
		return DataManager.get(getContext()).queryDecorations();
	}
}
