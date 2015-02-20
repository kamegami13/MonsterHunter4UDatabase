package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.WeaponDetailPagerAdapter;
import com.daviancorp.android.ui.dialog.WishlistDataAddDialogFragment;
import com.daviancorp.android.ui.general.GenericTabActivity;

public class WeaponDetailActivity extends GenericTabActivity implements
		ActionBar.TabListener {
	/** A key for passing a weapon ID as a long */
	public static final String EXTRA_WEAPON_ID =
			"com.daviancorp.android.android.ui.detail.weapon_id";

	private static final String DIALOG_WISHLIST_ADD = "wishlist_add";
	private static final int REQUEST_ADD = 0;
	
	private ViewPager viewPager;
	private WeaponDetailPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	private long id;
	private String name;
	
	// Tab titles
	private String[] tabs = { "Detail", "Family Tree", "Components"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		id = getIntent().getLongExtra(EXTRA_WEAPON_ID, -1);
		name = DataManager.get(getApplicationContext()).getWeapon(id).getName();
		setTitle(name);
		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new WeaponDetailPagerAdapter(getSupportFragmentManager(), getApplicationContext(), id);
		viewPager.setAdapter(mAdapter);

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

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
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_wishlist_add, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.wishlist_add:
				FragmentManager fm = getSupportFragmentManager();
				WishlistDataAddDialogFragment dialogCopy = WishlistDataAddDialogFragment
						.newInstance(id, name);
				dialogCopy.show(fm, DIALOG_WISHLIST_ADD);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
	}
	
	@Override
	public void onPause() {
		super.onPause();
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
