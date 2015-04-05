package com.daviancorp.android.data.classes;

public enum Rank {
    LOW(1, 3),
    HIGH(4, 7),
    G(8, 10);

    int armorMinimumRarity;
    int armorMaximumRarity;

    private Rank(int armorMinimumRarity, int armorMaximumRarity) {
        this.armorMinimumRarity = armorMinimumRarity;
        this.armorMaximumRarity = armorMaximumRarity;
    }

    public int getArmorMinimumRarity() {
        return armorMinimumRarity;
    }

    public int getArmorMaximumRarity() {
        return armorMaximumRarity;
    }
}
