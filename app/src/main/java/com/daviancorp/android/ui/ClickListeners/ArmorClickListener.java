package com.daviancorp.android.ui.ClickListeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.ArmorDetailActivity;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;

/**
 * Created by Mark on 2/24/2015.
 */
public class ArmorClickListener implements View.OnClickListener {

    private Context c;
    private Long id;
    private boolean fromArmorSetBuilder;
    private Activity activity;

    public ArmorClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    public ArmorClickListener(Context context, Long id, boolean fromArmorSetBuilder, Activity activity) {
        this(context, id);
        this.fromArmorSetBuilder = fromArmorSetBuilder;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, ArmorDetailActivity.class);
        i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, id);

        if (fromArmorSetBuilder) {
            i.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER, true);
            i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            c.startActivity(i);
            activity.finish();
        }
        else {
            c.startActivity(i);
        }
    }
}
