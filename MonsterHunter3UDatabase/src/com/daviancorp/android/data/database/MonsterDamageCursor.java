package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.MonsterDamage;

/**
 * A convenience class to wrap a cursor that returns rows from the "monster_damage"
 * table. The {@link getMonsterDamage()} method will give you a MonsterDamage instance
 * representing the current row.
 */
public class MonsterDamageCursor extends CursorWrapper {

	public MonsterDamageCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a MonsterDamage object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public MonsterDamage getMonsterDamage() {
		if (isBeforeFirst() || isAfterLast())
			return null;
		
		MonsterDamage monsterDamage = new MonsterDamage();
		
		long id = getLong(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_ID));
		String body_part = getString(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_BODY_PART));
		int cut = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_CUT));
		int impact = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_IMPACT));
		int shot = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_SHOT));
		int fire = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_FIRE));
		int water = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_WATER));
		int ice = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_ICE));
		int thunder = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_THUNDER));
		int dragon = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_DRAGON));
		int ko = getInt(getColumnIndex(S.COLUMN_MONSTER_DAMAGE_KO));
		
		monsterDamage.setId(id);
		monsterDamage.setBodyPart(body_part);
		monsterDamage.setCut(cut);
		monsterDamage.setImpact(impact);
		monsterDamage.setShot(shot);
		monsterDamage.setFire(fire);
		monsterDamage.setWater(water);
		monsterDamage.setIce(ice);
		monsterDamage.setThunder(thunder);
		monsterDamage.setDragon(dragon);
		monsterDamage.setKo(ko);

		return monsterDamage;
	}
}