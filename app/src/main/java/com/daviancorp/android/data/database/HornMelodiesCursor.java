package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.classes.Melody;
import com.daviancorp.android.data.classes.Skill;

/**
 * A convenience class to wrap a cursor that returns rows from the "horn_melodies"
 * table. The getMelody() method will give you a Melody instance
 * representing the current row.
 */
public class HornMelodiesCursor extends CursorWrapper {

    public HornMelodiesCursor(Cursor c) {
        super(c);
    }

    /**
     * Returns a Skill object configured for the current row, or null if the
     * current row is invalid.
     */
    public Melody getMelody() {
        if (isBeforeFirst() || isAfterLast())
            return null;

        Melody melody = new Melody();

        long id = getLong(getColumnIndex(S.COLUMN_HORN_MELODIES_ID));
        String notes = getString(getColumnIndex(S.COLUMN_HORN_MELODIES_NOTES));
        String song = getString(getColumnIndex(S.COLUMN_HORN_MELODIES_SONG));
        String effect1 = getString(getColumnIndex(S.COLUMN_HORN_MELODIES_EFFECT_1));
        String effect2 = getString(getColumnIndex(S.COLUMN_HORN_MELODIES_EFFECT_2));
        String duration = getString(getColumnIndex(S.COLUMN_HORN_MELODIES_DURATION));
        String extension = getString(getColumnIndex(S.COLUMN_HORN_MELODIES_EXTENSION));

        melody.setId(id);
        melody.setNotes(notes);
        melody.setSong(song);
        melody.setEffect1(effect1);
        melody.setEffect2(effect2);
        melody.setDuration(duration);
        melody.setExtension(extension);

        return melody;
    }
}