package com.daviancorp.android.ui.ClickListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.ArmorDetailActivity;

/**
 * Created by Mark on 2/24/2015.
 */
public class ArmorClickListener implements View.OnClickListener {
    private Context c;
    private Long id;

    public ArmorClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, ArmorDetailActivity.class);
        i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, id);
        c.startActivity(i);
    }
}
