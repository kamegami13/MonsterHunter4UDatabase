package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.daviancorp.android.data.database.DataManager;

public class QuestRewardListCursorLoader extends SQLiteCursorLoader {
	private String from;	// "item" or "quest"
	private long id; 		// Item or Quest id

	public QuestRewardListCursorLoader(Context context, String from, long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals("item")) {
			return DataManager.get(getContext()).queryQuestRewardItem(id);
		}
		else if(from.equals("quest")) {
			return DataManager.get(getContext()).queryQuestRewardQuest(id);
		}
		else {
			Log.d("heyo", "QuestRewardListCursorLoader: bad arg!!! + (" + from + ")");
			return null;
		}
	}
}
