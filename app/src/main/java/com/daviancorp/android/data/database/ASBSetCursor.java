package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.daviancorp.android.data.classes.ASBSet;

public class ASBSetCursor extends CursorWrapper {

    public ASBSetCursor(Cursor c) {
        super(c);
    }

    public ASBSet getASBSet() {
        ASBSet asbSet = new ASBSet();

        long id = getLong(getColumnIndex(S.COLUMN_ASB_SET_ID));
        String name = getString(getColumnIndex(S.COLUMN_ASB_SET_NAME));
        int rank = getInt(getColumnIndexOrThrow(S.COLUMN_ASB_SET_RANK));
        int hunterType = getInt(getColumnIndex(S.COLUMN_ASB_SET_HUNTER_TYPE));

        asbSet.setId(id);
        asbSet.setName(name);
        asbSet.setRank(rank);
        asbSet.setHunterType(hunterType);

        return asbSet;
    }
}
