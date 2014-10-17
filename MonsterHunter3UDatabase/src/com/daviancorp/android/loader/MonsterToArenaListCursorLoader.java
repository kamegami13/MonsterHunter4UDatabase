package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class MonsterToArenaListCursorLoader extends SQLiteCursorLoader {
	public static String FROM_MONSTER = "monster";
	public static String FROM_ARENA = "arena";
	
	private String from;	// "monster" or "arena"
	private long id; 		// Monster or ArenaQuest id

	public MonsterToArenaListCursorLoader(Context context, String from, long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals(FROM_MONSTER)) {
			// Query the list of arena quests based on monster
			return DataManager.get(getContext()).queryMonsterToArenaMonster(id);
		}
		else if(from.equals(FROM_ARENA)) {
			// Query the list of monsters based on arena quest
			return DataManager.get(getContext()).queryMonsterToArenaArena(id);
		}
		else {
			return null;
		}
	}
}
