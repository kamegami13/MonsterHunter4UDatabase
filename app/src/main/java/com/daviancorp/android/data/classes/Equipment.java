package com.daviancorp.android.data.classes;

/**
 * An intermediate class in between Item and Armor or other such classes that handles slotting of decorations.
 */
public class Equipment extends Item {

    private int slotCount;

    public Equipment() {
        this.slotCount = 0;
    }

    public int getNumSlots() {
        return slotCount;
    }

    public void setNumSlots(int num_slots) {
        this.slotCount = num_slots;
    }
}
