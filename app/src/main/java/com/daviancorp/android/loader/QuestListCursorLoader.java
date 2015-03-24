package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/*
 *  Refer to MonsterListPagerAdapter and MonsterListFragment on 
 *  how to call this loader
 */
public class QuestListCursorLoader extends SQLiteCursorLoader {
	public static String HUB_CARAVAN = "Caravan";
	public static String HUB_GUILD = "Guild";
	public static String HUB_EVENT = "Event";
	
	private String hub; 	// "Village", "Port", or "DLC"
	private String stars; 	// "1", "2", "3", "4", "5", "6", "7", "8", or
							// "9" (Only use 9 if hub = "Port")

	public QuestListCursorLoader(Context context, String hub, String stars) {
		super(context);
		this.hub = hub;
		this.stars = stars;
	}

	@Override
	protected Cursor loadCursor() {
		if (hub == null && stars == null) {
			// Query the list of all quests
			return DataManager.get(getContext()).queryQuests();
		} else if (stars == null) {
			// Query the list of quests based on hub
			return DataManager.get(getContext()).queryQuestHub(hub);
		} else {
			// Query the list of quests based on hub and stars
			return DataManager.get(getContext()).queryQuestHubStar(hub, stars);
		}
	}
}
