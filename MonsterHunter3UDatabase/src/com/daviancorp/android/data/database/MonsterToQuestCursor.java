package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.MonsterToQuest;
import com.daviancorp.android.data.object.Monster;
import com.daviancorp.android.data.object.Quest;

/**
 * A convenience class to wrap a cursor that returns rows from the "monster_to_quest"
 * table. The {@link getMonsterToQuest()} method will give you a MonsterToQuest instance
 * representing the current row.
 */
public class MonsterToQuestCursor extends CursorWrapper {

	public MonsterToQuestCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a MonsterToQuest object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public MonsterToQuest getMonsterToQuest() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		MonsterToQuest monster_to_quest = new MonsterToQuest();
		
		long id = getLong(getColumnIndex(S.COLUMN_MONSTER_TO_QUEST_ID));
		String unstable = getString(getColumnIndex(S.COLUMN_MONSTER_TO_QUEST_UNSTABLE));

		monster_to_quest.setId(id);
		monster_to_quest.setUnstable(unstable);

		Quest quest = new Quest();

		long questId = getLong(getColumnIndex(S.COLUMN_MONSTER_TO_QUEST_QUEST_ID));
		String questName = getString(getColumnIndex("q" + S.COLUMN_QUESTS_NAME));
//			String goal = getString(getColumnIndex(S.COLUMN_QUESTS_GOAL));
		String hub = getString(getColumnIndex(S.COLUMN_QUESTS_HUB));
//			String type = getString(getColumnIndex(S.COLUMN_QUESTS_TYPE));
		String stars = getString(getColumnIndex(S.COLUMN_QUESTS_STARS));
//			int timeLimit = getInt(getColumnIndex(S.COLUMN_QUESTS_TIME_LIMIT));
//			int fee = getInt(getColumnIndex(S.COLUMN_QUESTS_FEE));
//			int reward = getInt(getColumnIndex(S.COLUMN_QUESTS_REWARD));
//			int hrp = getInt(getColumnIndex(S.COLUMN_QUESTS_HRP));

		quest.setId(questId);
		quest.setName(questName);
//			quest.setGoal(goal);
		quest.setHub(hub);
//			quest.setType(type);
		quest.setStars(stars);
//			quest.setTimeLimit(timeLimit);
//			quest.setFee(fee);
//			quest.setReward(reward);
//			quest.setHrp(hrp);

		monster_to_quest.setQuest(quest);

		Monster monster = new Monster();

		long monsterId = getLong(getColumnIndex(S.COLUMN_MONSTER_TO_QUEST_MONSTER_ID));
		String monsterName = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_NAME));
//			String monsterClass = getString(getColumnIndex(S.COLUMN_MONSTERS_CLASS));
		String trait = getString(getColumnIndex(S.COLUMN_MONSTERS_TRAIT));
		String file_location = getString(getColumnIndex(S.COLUMN_MONSTERS_FILE_LOCATION)); 
		
		monster.setId(monsterId);
		monster.setName(monsterName);
//			monster.setMonsterClass(monsterClass);
		monster.setTrait(trait);
		monster.setFileLocation(file_location); 
		
		monster_to_quest.setMonster(monster);
		
		return monster_to_quest;
	}

}