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
import com.daviancorp.android.ui.general.GenericTabActivity;

public class ArmorListActivity extends GenericTabActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private ArmorExpandableListPagerAdapter mAdapter;
	private ActionBar actionBar;

	// Tab titles
	private String[] tabs = { "Blade", "Gunner", "Both" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.armor);

		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new ArmorExpandableListPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Enable drawer button instead of back button
        super.enableDrawerIndicator();

        // Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
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
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
