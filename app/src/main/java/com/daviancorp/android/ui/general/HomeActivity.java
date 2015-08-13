package com.daviancorp.android.ui.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class HomeActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.enableDrawerIndicator(); // Enable drawer button instead of back button
        setTitle(R.string.app_name);

    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.UNLISTED;
    }

    @Override
    protected Fragment createFragment() {
        super.detail = new HomeFragment();
        return super.detail;
    }
}
