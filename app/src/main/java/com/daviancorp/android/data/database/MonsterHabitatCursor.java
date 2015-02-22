package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.classes.Habitat;
import com.daviancorp.android.data.classes.Location;
import com.daviancorp.android.data.classes.Monster;

/**
 * Created by Mark on 2/22/2015.
 */
public class MonsterHabitatCursor extends CursorWrapper {

    /**
     * Default constructor
     * @param c
     */
    public MonsterHabitatCursor(Cursor c) {
        super(c);
    }

    /**
     * Generates a Habitat object after retrieving entries from the database
     * @return The habitat object
     */
    public Habitat getHabitat()
    {
        if (isBeforeFirst() || isAfterLast())
            return null;


        //Get base habitat info
        Habitat habitat = new Habitat();

        long id = getLong(getColumnIndex(S.COLUMN_HABITAT_ID));
        long start = getLong(getColumnIndex(S.COLUMN_HABITAT_START));
        long rest = getLong(getColumnIndex(S.COLUMN_HABITAT_REST));

        String areas = getString(getColumnIndex(S.COLUMN_HABITAT_AREAS));
        String[] allAreas = areas.split("/");

        long[] areasInt = new long[allAreas.length];
        for (int i = 0; i < allAreas.length; i++)
        {
            areasInt[i] = Long.valueOf(allAreas[i]);
        }

        habitat.setStart(start);
        habitat.setRest(rest);
        habitat.setAreas(areasInt);
        habitat.setId(id);

        //Get Location
        Location location = new Location();

        long loc_id = getLong(getColumnIndex("l" + S.COLUMN_LOCATIONS_ID));
        String loc_name = getString(getColumnIndex("l" + S.COLUMN_LOCATIONS_NAME));

        location.setId(loc_id);
        location.setName(loc_name);

        habitat.setLocation(location);

        //Get Monster
        Monster monster = new Monster();

        long mon_id = getLong(getColumnIndex("m" + S.COLUMN_MONSTERS_ID));
        String mon_name = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_NAME));
        String file = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_FILE_LOCATION));
        String mon_class = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_CLASS));

        monster.setId(mon_id);
        monster.setName(mon_name);
        monster.setFileLocation(file);
        monster.setMonsterClass(mon_class);

        habitat.setMonster(monster);

        return habitat;
    }
}
