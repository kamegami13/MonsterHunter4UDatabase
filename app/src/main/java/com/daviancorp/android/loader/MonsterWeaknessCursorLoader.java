package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class MonsterWeaknessCursorLoader extends SQLiteCursorLoader {
    private long id;

    public MonsterWeaknessCursorLoader(Context context, long id) {
        super(context);
        this.id = id;
    }

    @Override
    protected Cursor loadCursor() {
        // Query the list of weaknesses from monster id
        return DataManager.get(getContext()).queryWeaknessFromMonster(id);
    }
}
