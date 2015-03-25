package com.daviancorp.android.data.classes;

public enum Rank {
    LOW("Low", 1, 3),
    HIGH("High", 4, 7),
    G("G", 8, 10);

    int armorMinimumRarity;
    int armorMaximumRarity;
    String properName;

    private Rank(String properName) {
        this.properName = properName;
    }

    private Rank(String properName, int armorMinimumRarity, int armorMaximumRarity) {
        this(properName);
        this.armorMinimumRarity = armorMinimumRarity;
        this.armorMaximumRarity = armorMaximumRarity;
    }

    public int getArmorMinimumRarity() {
        return armorMinimumRarity;
    }

    public int getArmorMaximumRarity() {
        return armorMaximumRarity;
    }

    public String getProperName() {
        return properName;
    }

    @Override
    public String toString() {
        return getProperName();
    }
}
