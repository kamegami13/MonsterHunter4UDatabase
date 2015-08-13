package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.classes.ArenaQuest;
import com.daviancorp.android.data.database.DataManager;

public class ArenaQuestLoader extends DataLoader<ArenaQuest> {
    private long mArenaQuestId;

    public ArenaQuestLoader(Context context, long id) {
        super(context);
        mArenaQuestId = id;
    }

    @Override
    public ArenaQuest loadInBackground() {
        // Query the specific arena quest
        return DataManager.get(getContext()).getArenaQuest(mArenaQuestId);
    }
}
