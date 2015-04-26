package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.classes.WyporiumTrade;
import com.daviancorp.android.data.database.DataManager;

public class WyporiumTradeLoader extends DataLoader<WyporiumTrade> {
    private long mTradeId;

    public WyporiumTradeLoader(Context context, long tradeId) {
        super(context);
        mTradeId = tradeId;
    }

    @Override
    public WyporiumTrade loadInBackground() {
        // Query the specific decoration
        return DataManager.get(getContext()).getWyporiumTrade(mTradeId);
    }
}
