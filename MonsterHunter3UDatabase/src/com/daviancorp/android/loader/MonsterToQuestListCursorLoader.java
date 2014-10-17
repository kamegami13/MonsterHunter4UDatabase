package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class MonsterToQuestListCursorLoader extends SQLiteCursorLoader {
	public static String FROM_MONSTER = "monster";
	public static String FROM_QUEST = "quest";
	
	private String from;	// "monster" or "quest"
	private long id; 		// Monster or Quest id

	public MonsterToQuestListCursorLoader(Context context, String from, long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals(FROM_MONSTER)) {
			// Query the list of quests based on monster
			return DataManager.get(getContext()).queryMonsterToQuestMonster(id);
		}
		else if(from.equals(FROM_QUEST)) {
			// Query the list of monsters based on quest
			return DataManager.get(getContext()).queryMonsterToQuestQuest(id);
		}
		else {
			return null;
		}
	}
}
