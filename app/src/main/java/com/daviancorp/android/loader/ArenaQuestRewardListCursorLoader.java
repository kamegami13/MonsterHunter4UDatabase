package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class ArenaQuestRewardListCursorLoader extends SQLiteCursorLoader {
	public static String FROM_ITEM = "item";
	public static String FROM_ARENA_QUEST = "arena";
	
	private String from; 		// "item" or "arena"
	private long id; 			// Item or ArenaQuest id

	public ArenaQuestRewardListCursorLoader(Context context, String from,
			long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals(FROM_ITEM)) {
			// Query the list of arena quests based on item
			return DataManager.get(getContext()).queryArenaRewardItem(id);
		} else if (from.equals(FROM_ARENA_QUEST)) {
			// Query the list of arena quests based on arena quest
			return DataManager.get(getContext()).queryArenaRewardArena(id);
		} else {
			return null;
		}
	}
}
