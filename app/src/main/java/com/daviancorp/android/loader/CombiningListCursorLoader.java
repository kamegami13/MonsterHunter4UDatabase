package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class CombiningListCursorLoader extends SQLiteCursorLoader {

    private long id;

    public CombiningListCursorLoader(Context context, long id) {
        super(context);
        this.id = id;
    }

    @Override
    protected Cursor loadCursor() {
        // Query the list of all combinings
        if (id == -1) {
            return DataManager.get(getContext()).queryCombinings();
        } else {
            return DataManager.get(getContext()).queryCombiningOnItemID(id);
        }
    }
}
