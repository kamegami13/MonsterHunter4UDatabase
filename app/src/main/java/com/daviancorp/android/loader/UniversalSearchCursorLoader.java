package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.MultiObjectCursor;

/**
 * Created by Carlos on 8/5/2015.
 */
public class UniversalSearchCursorLoader extends SQLiteCursorLoader {
    private String searchTerm;
    public UniversalSearchCursorLoader(Context context,
                                       String searchTerm) {
        super(context);
        this.searchTerm = searchTerm;
    }

    @Override
    protected Cursor loadCursor() {
        DataManager manager = DataManager.get(getContext());

        try {
            MultiObjectCursor.Builder builder = new MultiObjectCursor.Builder();
            builder.add(manager.queryMonstersSearch(searchTerm), "getMonster");
            builder.add(manager.queryQuestsSearch(searchTerm), "getQuest");
            builder.add(manager.queryItemSearch(searchTerm), "getItem");

            return builder.create();
        } catch (NoSuchMethodException ex) {
            // this error is unexpected post development, so wrap with RuntimeException
            throw new RuntimeException(ex);
        }
    }
}
