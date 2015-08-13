package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.classes.MonsterStatus;

/**
 * Created by Mark on 2/22/2015.
 * Cursor for a Monster Status Query
 */
public class MonsterStatusCursor extends CursorWrapper {
    public MonsterStatusCursor(Cursor c) {
        super(c);
    }

    /**
     * Get next status object of the cursor
     * @return A MonsterStatus object
     */
    public MonsterStatus getStatus() {
        if (isBeforeFirst() || isAfterLast())
            return null;

        MonsterStatus status = new MonsterStatus();

        String status_type = getString(getColumnIndex(S.COLUMN_MONSTER_STATUS_STATUS));
        long initial = getLong(getColumnIndex(S.COLUMN_MONSTER_STATUS_INITIAL));
        long increase = getLong(getColumnIndex(S.COLUMN_MONSTER_STATUS_INCREASE));
        long max = getLong(getColumnIndex(S.COLUMN_MONSTER_STATUS_MAX));
        long duration = getLong(getColumnIndex(S.COLUMN_MONSTER_STATUS_DURATION));
        long damage = getLong(getColumnIndex(S.COLUMN_MONSTER_STATUS_DAMAGE));

        status.setStatus(status_type);
        status.setInitial(initial);
        status.setIncrease(increase);
        status.setMax(max);
        status.setDuration(duration);
        status.setDamage(damage);

        return status;
    }

}
