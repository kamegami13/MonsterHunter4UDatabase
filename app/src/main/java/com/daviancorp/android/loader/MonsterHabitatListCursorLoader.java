package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/**
 * Created by Mark on 2/22/2015.
 */
public class MonsterHabitatListCursorLoader extends SQLiteCursorLoader {
    public static String FROM_MONSTER = "monster";
    public static String FROM_LOCATION = "location";

    private String from;    // "monster" or "location"
    private long id;        // monster or location id

    /**
     * Public constructor for the cursor loader
     * @param context The context of the loader
     * @param from    String of "monster" or "location"
     * @param id      id of monster or location to query
     */
    public MonsterHabitatListCursorLoader(Context context, String from, long id) {
        super(context);
        this.from = from;
        this.id = id;
    }

    @Override
    /**
     * Loads cursor based upon which query we're coming from
     */
    protected Cursor loadCursor() {
        if (from.equals(FROM_MONSTER)) {
            return DataManager.get(getContext()).queryHabitatMonster(id);
        } else if (from.equals(FROM_LOCATION)) {
            return DataManager.get(getContext()).queryHabitatLocation(id);
        } else {
            return null;
        }
    }
}
