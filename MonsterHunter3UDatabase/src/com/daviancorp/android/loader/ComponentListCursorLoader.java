package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.daviancorp.android.data.database.DataManager;

public class ComponentListCursorLoader extends SQLiteCursorLoader {
	private String from;	// "created" or "component"
	private long id; 		// Item id

	public ComponentListCursorLoader(Context context, String from, long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals("created")) {
			return DataManager.get(getContext()).queryComponentCreated(id);
		}
		else if(from.equals("component")) {
			return DataManager.get(getContext()).queryComponentComponent(id);
		}
		else {
			Log.d("heyo", "ComponentCursorLoader: bad arg!!! + (" + from + ")");
			return null;
		}
	}
}
