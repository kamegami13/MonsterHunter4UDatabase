package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class MonsterAilmentCursorLoader extends SQLiteCursorLoader {
    private long id; 		// Notes available in a horn

    public MonsterAilmentCursorLoader(Context context, long id) {
        super(context);
        this.id = id;
    }

    @Override
    protected Cursor loadCursor() {
        // Query the list of skills from a skill tree
        return DataManager.get(getContext()).queryAilmentsFromId(id);
    }
}
