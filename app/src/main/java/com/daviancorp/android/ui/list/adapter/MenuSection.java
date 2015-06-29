package com.daviancorp.android.ui.list.adapter;

/**
 * 2015
 * Created by habibokanla on 01/03/15.
 */
public enum MenuSection {

    UNLISTED(-1),
    MONSTERS(0),
    WEAPONS(1),
    ARMOR(2),
    QUESTS(3),
    ITEMS(4),
    COMBINING(5),
    LOCATIONS(6),
    DECORATION(7),
    SKILL_TREES(8),
    WISH_LISTS(9),
    WYPORIUM_TRADE(10),
    ARMOR_SET_BUILDER(11);

    public int menuListPosition;

    MenuSection(int position) {
        this.menuListPosition = position;
    }
}
