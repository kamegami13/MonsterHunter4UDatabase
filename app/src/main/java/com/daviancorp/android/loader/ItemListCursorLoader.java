package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class ItemListCursorLoader extends SQLiteCursorLoader {

    private String filter;

    public ItemListCursorLoader(Context context, String filter) {
        super(context);
        this.filter = filter;
    }

    @Override
    protected Cursor loadCursor() {
        if (filter == null || filter.equals("")) {
            // Query the list of all items
            return DataManager.get(getContext()).queryItems();
        } else {
            // Query the list of items by filter
            return DataManager.get(getContext()).queryItemSearch(filter);
        }
    }
}
