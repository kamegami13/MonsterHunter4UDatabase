package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.daviancorp.android.data.database.DataManager;

public class ArenaQuestRewardListCursorLoader extends SQLiteCursorLoader {
	private String from;	// "item" or "arena"
	private long id; 		// Item or ArenaQuest id

	public ArenaQuestRewardListCursorLoader(Context context, String from, long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals("item")) {
			return DataManager.get(getContext()).queryArenaRewardItem(id);
		}
		else if(from.equals("arena")) {
			return DataManager.get(getContext()).queryArenaRewardArena(id);
		}
		else {
			Log.d("heyo", "ArenaQuestRewardListCursorLoader: bad arg!!! + (" + from + ")");
			return null;
		}
	}
}
