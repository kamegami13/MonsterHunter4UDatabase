package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class WyporiumTradeListCursorLoader extends SQLiteCursorLoader {

    public WyporiumTradeListCursorLoader(Context context) {
        super(context);
    }

    @Override
    protected Cursor loadCursor() {
        // Query the list of all wyporium trades
        return DataManager.get(getContext()).queryWyporiumTrades();
    }
}
