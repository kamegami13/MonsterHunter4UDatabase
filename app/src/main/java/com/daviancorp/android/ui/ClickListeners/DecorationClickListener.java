package com.daviancorp.android.ui.ClickListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.DecorationDetailActivity;

/**
 * Created by Mark on 2/24/2015.
 */
public class DecorationClickListener implements View.OnClickListener {
    private Context c;
    private Long id;

    public DecorationClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, DecorationDetailActivity.class);
        i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, id);
        c.startActivity(i);
    }
}
