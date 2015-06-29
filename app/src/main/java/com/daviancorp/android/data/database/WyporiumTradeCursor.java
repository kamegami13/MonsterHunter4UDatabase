package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.classes.WyporiumTrade;

public class WyporiumTradeCursor extends CursorWrapper {

    public WyporiumTradeCursor(Cursor c) {
        super(c);
    }

    public WyporiumTrade getWyporiumTrade() {
        if (isBeforeFirst() || isAfterLast())
            return null;

        WyporiumTrade wyporiumTrade = new WyporiumTrade();

        long wyporiumTradeId = getLong(getColumnIndex("trade_id"));
        long itemInId = getLong(getColumnIndex("in_id"));
        String itemInName = getString(getColumnIndex("in_name"));
        String itemInIconName = getString(getColumnIndex("in_icon_name"));
        long itemOutId = getLong(getColumnIndex("out_id"));
        String itemOutName = getString(getColumnIndex("out_name"));
        String itemOutIconName = getString(getColumnIndex("out_icon_name"));
        long unlockQuestId = getLong(getColumnIndex("q_id"));
        String unlockQuestName = getString(getColumnIndex("q_name"));


        wyporiumTrade.setId(wyporiumTradeId);
        wyporiumTrade.setItemInId(itemInId);
        wyporiumTrade.setItemInName(itemInName);
        wyporiumTrade.setItemInIconName(itemInIconName);
        wyporiumTrade.setItemOutId(itemOutId);
        wyporiumTrade.setItemOutName(itemOutName);
        wyporiumTrade.setItemOutIconName(itemOutIconName);
        wyporiumTrade.setUnlockQuestId(unlockQuestId);
        wyporiumTrade.setUnlockQuestName(unlockQuestName);

        return wyporiumTrade;
    }
}
