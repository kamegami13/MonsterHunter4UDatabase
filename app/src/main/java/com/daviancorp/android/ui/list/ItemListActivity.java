package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class ItemListActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.items);

        // Tag as top level activity
        super.setAsTopLevel();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ITEMS;
    }

    @Override
    protected Fragment createFragment() {
        super.detail = new ItemListFragment();
        return super.detail;
    }
}
