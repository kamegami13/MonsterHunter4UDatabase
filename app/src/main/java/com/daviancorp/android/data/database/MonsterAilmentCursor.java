package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.classes.MonsterAilment;
import com.daviancorp.android.data.classes.MonsterStatus;

/**
 * Created by Jayson on 3/21/2015
 * Cursor for a Monster Ailment Query
 */
public class MonsterAilmentCursor extends CursorWrapper {
    public MonsterAilmentCursor(Cursor c) {super(c);}

    /**
     * Get next status object of the cursor
     * @return A MonsterAilment object
     */
    public MonsterAilment getAilment()
    {
        if (isBeforeFirst() || isAfterLast())
            return null;

        MonsterAilment ailment = new MonsterAilment();

        long monster_id = getLong(getColumnIndex(S.COLUMN_AILMENT_MONSTER_ID));
        // String monster_name = getString(getColumnIndex(S.COLUMN_AILMENT_MONSTER_NAME)); Not used
        String strailment = getString(getColumnIndex(S.COLUMN_AILMENT_AILMENT));

        ailment.setId(monster_id);
        // ailment.setMonstername(monster_name);
        ailment.setAilment(strailment);

        return ailment;
    }

}
