package com.daviancorp.android.data.classes;

public class ArmorSetBuilderSession {
    private static Armor none = new Armor();

    private Armor head;
    private Armor body;
    private Armor arms;
    private Armor waist;
    private Armor legs;

    /** Default constructor. */
    public ArmorSetBuilderSession() {
        head = none;
        body = none;
        arms = none;
        waist = none;
        legs = none;
    }

    public boolean isHeadSelected() {
        return head != none;
    }

    public boolean isBodySelected() {
        return body != none;
    }

    public boolean isArmsSelected() {
        return arms != none;
    }

    public boolean isWaistSelected() {
        return waist != none;
    }

    public boolean isLegsSelected() {
        return legs != none;
    }

    public void setHead(Armor head) {
            this.head = head;
    }

    public void setBody(Armor body) {
            this.body = body;
    }

    public void setArms(Armor arms) {
            this.arms = arms;
    }

    public void setWaist(Armor waist) {
            this.waist = waist;
    }

    public void setLegs(Armor legs) {
            this.legs = legs;
    }

    public Armor getHead() {
        return head;
    }

    public Armor getBody() {
        return body;
    }

    public Armor getArms() {
        return arms;
    }

    public Armor getWaist() {
        return waist;
    }

    public Armor getLegs() {
        return legs;
    }
}
