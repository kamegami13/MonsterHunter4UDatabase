package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/*
 *  Refer to MonsterListPagerAdapter and MonsterListFragment on 
 *  how to call this loader
 */
public class QuestListCursorLoader extends SQLiteCursorLoader {
	private String hub; // "Village", "Port", or "DLC"
	private String stars; // "1", "2", "3", "4", "5", "6", "7", "8", or
							// "9" (Only use 9 if hub = "Port")

	public QuestListCursorLoader(Context context, String hub, String stars) {
		super(context);
		this.hub = hub;
		this.stars = stars;
	}

	@Override
	protected Cursor loadCursor() {
		if (hub == null && stars == null) {
			return DataManager.get(getContext()).queryQuests();
		} else if (stars == null) {
			return DataManager.get(getContext()).queryQuestHub(hub);
		} else {
			return DataManager.get(getContext()).queryQuestHubStar(hub, stars);
		}
	}
}
