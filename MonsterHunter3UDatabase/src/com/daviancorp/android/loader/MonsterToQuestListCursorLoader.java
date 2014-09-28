package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.daviancorp.android.data.database.DataManager;

public class MonsterToQuestListCursorLoader extends SQLiteCursorLoader {
	private String from;	// "monster" or "quest
	private long id; 		// Monster or Quest id

	public MonsterToQuestListCursorLoader(Context context, String from, long id) {
		super(context);
		this.from = from;
		this.id = id;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals("monster")) {
			return DataManager.get(getContext()).queryMonsterToQuestMonster(id);
		}
		else if(from.equals("quest")) {
			return DataManager.get(getContext()).queryMonsterToQuestQuest(id);
		}
		else {
			Log.d("heyo", "MonsterToQuestCursorLoader: bad arg!!! + (" + from + ")");
			return null;
		}
	}
}
