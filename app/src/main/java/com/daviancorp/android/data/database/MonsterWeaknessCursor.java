package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.classes.MonsterStatus;
import com.daviancorp.android.data.classes.MonsterWeakness;

/**
 * Created by Jayson on 4/6/2015.
 * Cursor for a Monster Status Query
 */
public class MonsterWeaknessCursor extends CursorWrapper {
    public MonsterWeaknessCursor(Cursor c) {super(c);}

    /**
     * Get next status object of the cursor
     * @return A MonsterWeakness object
     */
    public MonsterWeakness getWeakness()
    {
        if (isBeforeFirst() || isAfterLast())
            return null;

        MonsterWeakness weakness = new MonsterWeakness();

        String monstername = getString(getColumnIndex(S.COLUMN_WEAKNESS_MONSTER_NAME));
        String state = getString(getColumnIndex(S.COLUMN_WEAKNESS_STATE));
        int fire = getInt(getColumnIndex(S.COLUMN_WEAKNESS_FIRE));
        int water = getInt(getColumnIndex(S.COLUMN_WEAKNESS_WATER));
        int thunder = getInt(getColumnIndex(S.COLUMN_WEAKNESS_THUNDER));
        int ice = getInt(getColumnIndex(S.COLUMN_WEAKNESS_ICE));
        int dragon = getInt(getColumnIndex(S.COLUMN_WEAKNESS_DRAGON));
        int poison = getInt(getColumnIndex(S.COLUMN_WEAKNESS_POISON));
        int paralysis = getInt(getColumnIndex(S.COLUMN_WEAKNESS_PARALYSIS));
        int sleep = getInt(getColumnIndex(S.COLUMN_WEAKNESS_SLEEP));
        int pitfalltrap = getInt(getColumnIndex(S.COLUMN_WEAKNESS_PITFALL_TRAP));
        int shocktrap = getInt(getColumnIndex(S.COLUMN_WEAKNESS_SHOCK_TRAP));
        int flashbomb = getInt(getColumnIndex(S.COLUMN_WEAKNESS_FLASH_BOMB));
        int sonicbomb = getInt(getColumnIndex(S.COLUMN_WEAKNESS_SONIC_BOMB));
        int dungbomb = getInt(getColumnIndex(S.COLUMN_WEAKNESS_DUNG_BOMB));
        int meat = getInt(getColumnIndex(S.COLUMN_WEAKNESS_MEAT));

        weakness.setMonstername(monstername);
        weakness.setState(state);
        weakness.setFire(fire);
        weakness.setWater(water);
        weakness.setThunder(thunder);
        weakness.setIce(ice);
        weakness.setDragon(dragon);
        weakness.setPoison(poison);
        weakness.setParalysis(paralysis);
        weakness.setSleep(sleep);
        weakness.setPitfalltrap(pitfalltrap);
        weakness.setShocktrap(shocktrap);
        weakness.setFlashbomb(flashbomb);
        weakness.setSonicbomb(sonicbomb);
        weakness.setDungbomb(dungbomb);
        weakness.setMeat(meat);

        return weakness;
    }

}
