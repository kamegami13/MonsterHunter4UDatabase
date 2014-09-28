package com.daviancorp.android.loader;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Monster;

import android.content.Context;

public class MonsterLoader extends DataLoader<Monster> {
	private long mMonsterId;
	
	public MonsterLoader(Context context, long monsterId) {
		super(context);
		mMonsterId = monsterId;
	}
	
	@Override
	public Monster loadInBackground() {
		return DataManager.get(getContext()).getMonster(mMonsterId);
	}
}
