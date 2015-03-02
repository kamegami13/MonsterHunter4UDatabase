package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.view.Menu;
import android.view.MenuItem;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ArmorExpandableListPagerAdapter;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;
import com.daviancorp.android.ui.general.GenericTabActivity;

public class ArmorListActivity extends GenericTabActivity {

	private ViewPager viewPager;
	private ArmorExpandableListPagerAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.armor);

        boolean fromArmorSetBuilder = getIntent().getBooleanExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER, false);

		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new ArmorExpandableListPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);

		mSlidingTabLayout.setViewPager(viewPager);

        if (!fromArmorSetBuilder) {
            // If we're not coming from the armor set builder, we want to enable drawer button instead of back button
            super.enableDrawerIndicator();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.monsterlist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}

    @Override
    public void onPause() {
        super.onPause();
    }

}
