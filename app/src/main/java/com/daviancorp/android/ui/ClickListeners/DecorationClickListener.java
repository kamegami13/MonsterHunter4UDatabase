package com.daviancorp.android.ui.ClickListeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.ASBActivity;
import com.daviancorp.android.ui.detail.DecorationDetailActivity;

public class DecorationClickListener implements View.OnClickListener {
    private Context c;
    private Long id;

    private boolean fromAsb;
    private Activity activity;

    public DecorationClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    public DecorationClickListener(Context context, Long id, boolean fromAsb, Activity activity) {
        this(context, id);
        this.fromAsb = fromAsb;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, DecorationDetailActivity.class);
        i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, id);

        if (fromAsb) {
            i.putExtras(activity.getIntent());
            activity.startActivityForResult(i, ASBActivity.REQUEST_CODE_ADD_DECORATION);
        } else {
            c.startActivity(i);
        }
    }
}
