package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.classes.Monster;
import com.daviancorp.android.data.database.DataManager;

public class MonsterLoader extends DataLoader<Monster> {
    private long mMonsterId;

    public MonsterLoader(Context context, long monsterId) {
        super(context);
        mMonsterId = monsterId;
    }

    @Override
    public Monster loadInBackground() {
        // Query the specific monster
        return DataManager.get(getContext()).getMonster(mMonsterId);
    }
}
