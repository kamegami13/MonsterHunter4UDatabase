package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class WyporiumTradeListActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.wyporiumtrade);

        // Enable drawer button instead of back button
        super.enableDrawerIndicator();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.WYPORIUM_TRADE;
    }

    @Override
    protected Fragment createFragment() {
        super.detail = new WyporiumTradeListFragment();
        return super.detail;
    }

}
