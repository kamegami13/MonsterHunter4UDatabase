package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Decoration;

public class DecorationLoader extends DataLoader<Decoration> {
	private long mDecorationId;
	
	public DecorationLoader(Context context, long decorationId) {
		super(context);
		mDecorationId = decorationId;
	}
	
	@Override
	public Decoration loadInBackground() {
		return DataManager.get(getContext()).getDecoration(mDecorationId);
	}
}
