package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import com.daviancorp.android.data.database.DataManager;

public class ASBSetListCursorLoader extends SQLiteCursorLoader {

    public ASBSetListCursorLoader(Context context) {
        super(context);
    }

    @Override
    protected Cursor loadCursor() {
        return DataManager.get(getContext()).queryASBSets();
    }
}
