package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class HornMelodyListCursorLoader extends SQLiteCursorLoader {
    private String notes; 		// Notes available in a horn

    public HornMelodyListCursorLoader(Context context, String notes) {
        super(context);
        this.notes = notes;
    }

    @Override
    protected Cursor loadCursor() {
        // Query the list of skills from a skill tree
        return DataManager.get(getContext()).queryMelodiesFromNotes(notes);
    }
}
