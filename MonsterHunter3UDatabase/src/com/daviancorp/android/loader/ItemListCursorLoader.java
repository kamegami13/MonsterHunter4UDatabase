package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class ItemListCursorLoader extends SQLiteCursorLoader {
	
	private String filter;
	public ItemListCursorLoader(Context context, String filter) {
		super(context);
		this.filter = filter;
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all items
		if (filter == null || filter.equals("")) {
			return DataManager.get(getContext()).queryItems();
		}
		else {
			return DataManager.get(getContext()).queryItemSearch(filter);
		}
	}
}
