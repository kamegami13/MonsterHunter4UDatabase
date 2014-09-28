package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.Location;
import com.daviancorp.android.data.object.Quest;

/**
 * A convenience class to wrap a cursor that returns rows from the "quests"
 * table. The {@link getQuest()} method will give you a Quest instance
 * representing the current row.
 */
public class QuestCursor extends CursorWrapper {

	public QuestCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a Quest object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public Quest getQuest() {
		if (isBeforeFirst() || isAfterLast())
			return null;
		
		Quest quest = new Quest();
		Location location = new Location();

		long questId = getLong(getColumnIndex(S.COLUMN_QUESTS_ID));
		String name = getString(getColumnIndex("q" + S.COLUMN_QUESTS_NAME));
		String goal = getString(getColumnIndex(S.COLUMN_QUESTS_GOAL));
		String hub = getString(getColumnIndex(S.COLUMN_QUESTS_HUB));
		String type = getString(getColumnIndex(S.COLUMN_QUESTS_TYPE));
		String stars = getString(getColumnIndex(S.COLUMN_QUESTS_STARS));
		long locationId = getLong(getColumnIndex(S.COLUMN_QUESTS_LOCATION_ID));
		int timeLimit = getInt(getColumnIndex(S.COLUMN_QUESTS_TIME_LIMIT));
		int fee = getInt(getColumnIndex(S.COLUMN_QUESTS_FEE));
		int reward = getInt(getColumnIndex(S.COLUMN_QUESTS_REWARD));
		int hrp = getInt(getColumnIndex(S.COLUMN_QUESTS_HRP));

		quest.setId(questId);
		quest.setName(name);
		quest.setGoal(goal);
		quest.setHub(hub);
		quest.setType(type);
		quest.setStars(stars);
		quest.setTimeLimit(timeLimit);
		quest.setFee(fee);
		quest.setReward(reward);
		quest.setHrp(hrp);
		location.setId(locationId);
		
		String locName = getString(getColumnIndex("l" + S.COLUMN_LOCATIONS_NAME));
		String fileLocation = getString(getColumnIndex(S.COLUMN_LOCATIONS_MAP));
		
		location.setName(locName);
		location.setFileLocation(fileLocation);
		
		quest.setLocation(location);
		
		return quest;
	}

}