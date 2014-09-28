package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.ArenaQuest;
import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.data.object.Location;

/**
 * A convenience class to wrap a cursor that returns rows from the "arena_quest"
 * table. The {@link getArenaQuest()} method will give you a ArenaQuest instance
 * representing the current row.
 */
public class ArenaQuestCursor extends CursorWrapper {

	public ArenaQuestCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a ArenaQuest object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public ArenaQuest getArenaQuest() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		ArenaQuest arenaQuest = new ArenaQuest();
		
		long id = getLong(getColumnIndex(S.COLUMN_ARENA_QUESTS_ID));
		String name = getString(getColumnIndex("a" + S.COLUMN_ARENA_QUESTS_NAME));
		String goal = getString(getColumnIndex(S.COLUMN_ARENA_QUESTS_GOAL));
		int reward = getInt(getColumnIndex(S.COLUMN_ARENA_QUESTS_REWARD));
		int num_participants = getInt(getColumnIndex(S.COLUMN_ARENA_QUESTS_NUM_PARTICIPANTS));
		String time_s = getString(getColumnIndex(S.COLUMN_ARENA_QUESTS_TIME_S));
		String time_a = getString(getColumnIndex(S.COLUMN_ARENA_QUESTS_TIME_A));
		String time_b = getString(getColumnIndex(S.COLUMN_ARENA_QUESTS_TIME_B));
		
		arenaQuest.setId(id);
		arenaQuest.setName(name);
		arenaQuest.setGoal(goal);
		arenaQuest.setReward(reward);
		arenaQuest.setNumParticipants(num_participants);
		arenaQuest.setTimeS(time_s);
		arenaQuest.setTimeA(time_a);
		arenaQuest.setTimeB(time_b);

		Location location = new Location();

		long locationId = getLong(getColumnIndex(S.COLUMN_ARENA_QUESTS_LOCATION_ID));
		String locationName = getString(getColumnIndex("l" + S.COLUMN_LOCATIONS_NAME));
//			String fileLocation = getString(getColumnIndex(S.COLUMN_LOCATIONS_MAP));

		location.setId(locationId);
		location.setName(locationName);
//			location.setFileLocation(fileLocation);
		
		arenaQuest.setLocation(location);
		
		return arenaQuest;
	}

}