package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class MonsterAilmentCursorLoader extends SQLiteCursorLoader {
    private long id;

    public MonsterAilmentCursorLoader(Context context, long id) {
        super(context);
        this.id = id;
    }

    @Override
    protected Cursor loadCursor() {
        // Query the list of ailments from monster id
        return DataManager.get(getContext()).queryAilmentsFromId(id);
    }
}
