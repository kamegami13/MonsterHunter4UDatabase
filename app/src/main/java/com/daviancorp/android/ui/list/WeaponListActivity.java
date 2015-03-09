package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class WeaponListActivity extends GenericActivity {
    public static final String EXTRA_WEAPON_TYPE =
            "com.daviancorp.android.android.ui.list.weapon_type";

    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        type = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString(EXTRA_WEAPON_TYPE);
        }

        super.onCreate(savedInstanceState);
        setTitle(type);

    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.WEAPONS;
    }

    @Override
    protected Fragment createFragment() {

        if (type.equals("Bow")) {
            super.detail = WeaponBowExpandableFragment.newInstance(type);
        } else if (type.equals("Light Bowgun") || type.equals("Heavy Bowgun")) {
            super.detail = WeaponBowgunExpandableFragment.newInstance(type);
        } else {
            super.detail = WeaponBladeExpandableFragment.newInstance(type);
        }
        return super.detail;
    }

}
