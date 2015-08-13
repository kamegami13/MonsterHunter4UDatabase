package com.daviancorp.android.ui.ClickListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daviancorp.android.ui.detail.QuestDetailActivity;

public class QuestClickListener implements View.OnClickListener {
    private Context c;
    private Long id;

    public QuestClickListener(Context context, Long id) {
        super();
        this.id = id;
        this.c = context;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(c, QuestDetailActivity.class);
        i.putExtra(QuestDetailActivity.EXTRA_QUEST_ID, id);
        c.startActivity(i);
    }
}