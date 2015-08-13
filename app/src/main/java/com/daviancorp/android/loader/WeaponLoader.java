package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.DataManager;

public class WeaponLoader extends DataLoader<Weapon> {
    private long mWeaponId;

    public WeaponLoader(Context context, long weaponId) {
        super(context);
        mWeaponId = weaponId;
    }

    @Override
    public Weapon loadInBackground() {
        // Query the specific weapon
        return DataManager.get(getContext()).getWeapon(mWeaponId);
    }
}
