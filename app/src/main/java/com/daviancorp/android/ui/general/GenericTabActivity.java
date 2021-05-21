package com.daviancorp.android.ui.general;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;

/*
 * Any subclass needs to:
 *  - override onCreate() to set title
 */

public abstract class GenericTabActivity extends GenericActionBarActivity {

	protected Fragment detail;
    protected SlidingTabLayout mSlidingTabLayout;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab);

        // Integrate Toolbar so sliding drawer can go over toolbar
        androidx.appcompat.widget.Toolbar mtoolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mtoolbar);

        // Set up tabs
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.sliding_tab_layout, R.id.text1);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        int width = size.x;

        mSlidingTabLayout.setMinimumWidth(width);
        mSlidingTabLayout.setDistributeEvenly(true);


        setTitle(R.string.app_name);
        super.setupDrawer(); // Needs to be called after setContentView
        // Disabled by request. Turns into BACK button
        //super.enableDrawerIndicator(); // Enable drawer toggle button
	}
}
