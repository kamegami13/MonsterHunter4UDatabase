package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Weapon;

public class WeaponLoader extends DataLoader<Weapon> {
	private long mWeaponId;
	
	public WeaponLoader(Context context, long weaponId) {
		super(context);
		mWeaponId = weaponId;
	}
	
	@Override
	public Weapon loadInBackground() {
		return DataManager.get(getContext()).getWeapon(mWeaponId);
	}
}
