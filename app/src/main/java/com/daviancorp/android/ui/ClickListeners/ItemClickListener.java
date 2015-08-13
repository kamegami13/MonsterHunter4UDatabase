package com.daviancorp.android.ui.ClickListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.ItemDetailActivity;

public class ItemClickListener implements View.OnClickListener {
    private Context c;
    private Long id;

    public ItemClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, ItemDetailActivity.class);
        i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, id);
        c.startActivity(i);
    }
}
