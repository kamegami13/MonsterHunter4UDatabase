package com.daviancorp.android.ui.general;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.list.adapter.MenuSection;

/*
 * The home screen activity upon starting the application
 */
@SuppressLint("NewApi")
public class HomeActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//		getSupportActionBar().setHomeButtonEnabled(false);
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
