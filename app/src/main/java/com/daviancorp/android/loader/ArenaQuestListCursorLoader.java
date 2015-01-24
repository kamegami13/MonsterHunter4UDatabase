package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class ArenaQuestListCursorLoader extends SQLiteCursorLoader {

	public ArenaQuestListCursorLoader(Context context) {
		super(context);
	}

	@Override
	protected Cursor loadCursor() {
		// Query the list of all arena quests
		return DataManager.get(getContext()).queryArenaQuests();
	}
}
