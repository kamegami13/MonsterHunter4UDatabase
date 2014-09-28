package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.ArenaQuest;

public class ArenaQuestLoader extends DataLoader<ArenaQuest> {
	private long mArenaQuestId;
	
	public ArenaQuestLoader(Context context, long id) {
		super(context);
		mArenaQuestId = id;
	}
	
	@Override
	public ArenaQuest loadInBackground() {
		return DataManager.get(getContext()).getArenaQuest(mArenaQuestId);
	}
}
