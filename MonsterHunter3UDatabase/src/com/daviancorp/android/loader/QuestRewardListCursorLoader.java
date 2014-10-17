package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class QuestRewardListCursorLoader extends SQLiteCursorLoader {
	public static String FROM_ITEM = "item";
	public static String FROM_QUEST = "quest";
	
	private String from;	// "item" or "quest"
	private long id; 		// Item or Quest id

	public QuestRewardListCursorLoader(Context context, String from, long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals(FROM_ITEM)) {
			// Query the list of quest rewards based on item
			return DataManager.get(getContext()).queryQuestRewardItem(id);
		}
		else if(from.equals(FROM_QUEST)) {
			// Query the list of quest rewards based on quest
			return DataManager.get(getContext()).queryQuestRewardQuest(id);
		}
		else {
			return null;
		}
	}
}
