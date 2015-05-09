package com.daviancorp.android.data.classes;

/**
 * The external part of an Armor Set Builder set. Holds metadata, such as the set's name.
 */
public class ASBSet {
    private long id;
    private String name;
    private int rank;
    private int hunterType; // 0 is undefined, 1 is blademaster, 2 is gunner

    public ASBSet() {
        id = -1;
        name = "";
        rank = -1;
        hunterType = -1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getHunterType() {
        return hunterType;
    }

    public void setHunterType(int hunterType) {
        this.hunterType = hunterType;
    }
}
