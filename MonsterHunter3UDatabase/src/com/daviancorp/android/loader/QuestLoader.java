package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.classes.Quest;
import com.daviancorp.android.data.database.DataManager;

public class QuestLoader extends DataLoader<Quest> {
	private long mQuestId;
	
	public QuestLoader(Context context, long questId) {
		super(context);
		mQuestId = questId;
	}
	
	@Override
	public Quest loadInBackground() {
		// Query the specific quest
		return DataManager.get(getContext()).getQuest(mQuestId);
	}
}
