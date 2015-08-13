package com.daviancorp.android.data.classes;

/*
 * Class for Monster Ailments
 *
 * Shows all the ailments a monster can inflict upon you
 */

public class MonsterAilment {

    private long id;            // Monster id
    private String monstername;    // Monster Name
    private String ailment;        // Name of status inflicted by monster


    /* Default Constructor */
    public MonsterAilment() {
        this.id = -1;
        this.monstername = "";
        this.ailment = "";
    }

    /* Getters and Setters */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonstername() {
        return monstername;
    }

    public void setMonstername(String monstername) {
        this.monstername = monstername;
    }

    public String getAilment() {
        return ailment;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
    }
}
