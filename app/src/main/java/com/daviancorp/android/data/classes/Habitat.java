package com.daviancorp.android.data.classes;

/**
 * Created by Mark on 2/22/2015.
 * Describes a habitat for a monster
 */
public class Habitat {
    private long id;             //id of habitat entry
    private Monster monster;     //id of the monster
    private Location location;   //if of habitat location
    private long start;          //Starting area number
    private long[] areas;        //Array of areas
    private long rest;           //Rest area of the monster

    /**
     * Default constructor
     * Initializes variables to defaults
     */
    public Habitat()
    {
        this.monster = null;
        this.location = null;
        this.areas = null;
        start = 0;
        rest = 0;
        id = -1;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long[] getAreas() {
        return areas;
    }

    public void setAreas(long[] areas) {
        this.areas = areas;
    }

    public long getRest() {
        return rest;
    }

    public void setRest(long rest) {
        this.rest = rest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
