package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Item;

public class ItemLoader extends DataLoader<Item> {
	private long mItemId;
	
	public ItemLoader(Context context, long itemId) {
		super(context);
		mItemId = itemId;
	}
	
	@Override
	public Item loadInBackground() {
		return DataManager.get(getContext()).getItem(mItemId);
	}
}
