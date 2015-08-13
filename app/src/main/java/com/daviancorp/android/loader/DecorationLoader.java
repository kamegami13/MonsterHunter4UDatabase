package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.classes.Decoration;
import com.daviancorp.android.data.database.DataManager;

public class DecorationLoader extends DataLoader<Decoration> {
    private long mDecorationId;

    public DecorationLoader(Context context, long decorationId) {
        super(context);
        mDecorationId = decorationId;
    }

    @Override
    public Decoration loadInBackground() {
        // Query the specific decoration
        return DataManager.get(getContext()).getDecoration(mDecorationId);
    }
}
