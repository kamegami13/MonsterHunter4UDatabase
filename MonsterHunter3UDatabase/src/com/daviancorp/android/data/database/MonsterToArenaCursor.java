package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.MonsterToArena;
import com.daviancorp.android.data.object.Monster;
import com.daviancorp.android.data.object.ArenaQuest;

/**
 * A convenience class to wrap a cursor that returns rows from the "monster_to_arena"
 * table. The {@link getMonsterToArena()} method will give you a MonsterToArena instance
 * representing the current row.
 */
public class MonsterToArenaCursor extends CursorWrapper {

	public MonsterToArenaCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a MonsterToArena object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public MonsterToArena getMonsterToArena() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		MonsterToArena monster_to_arena = new MonsterToArena();
		
		long id = getLong(getColumnIndex(S.COLUMN_MONSTER_TO_ARENA_ID));
		monster_to_arena.setId(id);

		ArenaQuest arenaQuest = new ArenaQuest();

		long arenaId = getLong(getColumnIndex(S.COLUMN_MONSTER_TO_ARENA_ARENA_ID));
		String arenaName = getString(getColumnIndex("a" + S.COLUMN_ARENA_QUESTS_NAME));

		arenaQuest.setId(arenaId);
		arenaQuest.setName(arenaName);

		monster_to_arena.setArenaQuest(arenaQuest);

		Monster monster = new Monster();

		long monsterId = getLong(getColumnIndex(S.COLUMN_MONSTER_TO_ARENA_MONSTER_ID));
		String monsterName = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_NAME));
//			String monsterClass = getString(getColumnIndex(S.COLUMN_MONSTERS_CLASS));
		String trait = getString(getColumnIndex(S.COLUMN_MONSTERS_TRAIT));
		String file_location = getString(getColumnIndex(S.COLUMN_MONSTERS_FILE_LOCATION)); 
		
		monster.setId(monsterId);
		monster.setName(monsterName);
//			monster.setMonsterClass(monsterClass);
		monster.setTrait(trait);
		monster.setFileLocation(file_location); 
		
		monster_to_arena.setMonster(monster);
		
		return monster_to_arena;
	}

}