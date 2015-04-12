package com.daviancorp.android.ui.ClickListeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.ArmorDetailActivity;
import com.daviancorp.android.ui.detail.ASBActivity;

/**
 * Created by Mark on 2/24/2015.
 */
public class ArmorClickListener implements View.OnClickListener {

    private Context c;
    private Long id;
    private boolean fromAsb;
    private Activity activity;

    public ArmorClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    public ArmorClickListener(Context context, Long id, boolean fromAsb, Activity activity) {
        this(context, id);
        this.fromAsb = fromAsb;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, ArmorDetailActivity.class);
        i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, id);

        if (fromAsb) {
            i.putExtras(activity.getIntent());
            activity.startActivityForResult(i, ASBActivity.REQUEST_CODE_ADD_PIECE);
        }
        else {
            c.startActivity(i);
        }
    }
}
