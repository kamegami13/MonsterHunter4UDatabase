package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class WishlistListCursorLoader extends SQLiteCursorLoader {

	public WishlistListCursorLoader(Context context) {
		super(context);
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all skill trees
		return DataManager.get(getContext()).queryWishlists();
	}
}
