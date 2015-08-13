package com.daviancorp.android.loader;

import android.content.Context;

import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.data.database.DataManager;

public class ASBSessionLoader extends DataLoader<ASBSession> {

    private long id;

    public ASBSessionLoader(Context context, long id) {
        super(context);
        this.id = id;
    }

    @Override
    public ASBSession loadInBackground() {
        return DataManager.get(getContext()).getASBSession(id);
    }
}
