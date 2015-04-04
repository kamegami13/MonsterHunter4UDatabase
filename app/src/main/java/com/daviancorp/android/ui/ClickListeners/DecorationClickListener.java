package com.daviancorp.android.ui.ClickListeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;
import com.daviancorp.android.ui.detail.DecorationDetailActivity;

/**
 * Created by Mark on 2/24/2015.
 */
public class DecorationClickListener implements View.OnClickListener {
    private Context c;
    private Long id;

    private boolean fromArmorSetBuilder;
    private Activity activity;

    public DecorationClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    public DecorationClickListener(Context context, Long id, boolean fromArmorSetBuilder, Activity activity) {
        this(context, id);
        this.fromArmorSetBuilder = fromArmorSetBuilder;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, DecorationDetailActivity.class);
        i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, id);

        if (fromArmorSetBuilder) {
            i.putExtras(activity.getIntent());
            activity.startActivityForResult(i, ArmorSetBuilderActivity.REQUEST_CODE_ADD_DECORATION);
        }
        else {
            c.startActivity(i);
        }
    }
}
