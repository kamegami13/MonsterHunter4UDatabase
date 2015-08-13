package com.daviancorp.android.ui.ClickListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.WeaponDetailActivity;

public class WeaponClickListener implements View.OnClickListener {
    private Context c;
    private Long id;

    public WeaponClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, WeaponDetailActivity.class);
        i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, id);
        c.startActivity(i);
    }
}
