package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class WishlistDataListCursorLoader extends SQLiteCursorLoader {

	private long id;
	
	public WishlistDataListCursorLoader(Context context, long id) {
		super(context);
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		return DataManager.get(getContext()).queryWishlistData(id);
	}
}
