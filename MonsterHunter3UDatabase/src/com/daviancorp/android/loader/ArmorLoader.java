package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Armor;

public class ArmorLoader extends DataLoader<Armor> {
	private long mArmorId;
	
	public ArmorLoader(Context context, long armorId) {
		super(context);
		mArmorId = armorId;
	}
	
	@Override
	public Armor loadInBackground() {
		return DataManager.get(getContext()).getArmor(mArmorId);
	}
}
