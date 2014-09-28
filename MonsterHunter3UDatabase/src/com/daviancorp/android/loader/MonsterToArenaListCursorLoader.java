package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.daviancorp.android.data.database.DataManager;

public class MonsterToArenaListCursorLoader extends SQLiteCursorLoader {
	private String from;	// "monster" or "arena"
	private long id; 		// Monster or ArenaQuest id

	public MonsterToArenaListCursorLoader(Context context, String from, long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals("monster")) {
			return DataManager.get(getContext()).queryMonsterToArenaMonster(id);
		}
		else if(from.equals("arena")) {
			return DataManager.get(getContext()).queryMonsterToArenaArena(id);
		}
		else {
			Log.d("heyo", "MonsterToArenaCursorLoader: bad arg!!! + (" + from + ")");
			return null;
		}
	}
}
