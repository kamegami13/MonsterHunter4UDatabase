package com.daviancorp.android.ui.ClickListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.LocationDetailActivity;

public class LocationClickListener implements View.OnClickListener {
    private Context c;
    private Long id;

    public LocationClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, LocationDetailActivity.class);
        i.putExtra(LocationDetailActivity.EXTRA_LOCATION_ID, id);
        c.startActivity(i);
    }
}