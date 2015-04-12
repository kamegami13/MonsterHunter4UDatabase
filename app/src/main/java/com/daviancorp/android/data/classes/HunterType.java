package com.daviancorp.android.data.classes;

public enum HunterType {
    UNDEFINED(2),
    BLADEMASTER(0),
    GUNNER(1);

    private int pageIndex;

    HunterType(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }
}
