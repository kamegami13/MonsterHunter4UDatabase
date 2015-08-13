package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class WishlistListActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.wishlist);

        // Tag as top level activity
        super.setAsTopLevel();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.WISH_LISTS;
    }

    @Override
    protected Fragment createFragment() {
        super.detail = new WishlistListFragment();
        return super.detail;
    }

}
