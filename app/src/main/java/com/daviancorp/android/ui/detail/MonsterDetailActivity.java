package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.view.Menu;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.MonsterDetailPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;

public class MonsterDetailActivity extends GenericTabActivity {
	/** A key for passing a monster ID as a long */
	public static final String EXTRA_MONSTER_ID =
			"com.daviancorp.android.android.ui.detail.monster_id";

	private ViewPager viewPager;
	private MonsterDetailPagerAdapter mAdapter;
	private ActionBar actionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		long id = getIntent().getLongExtra(EXTRA_MONSTER_ID, -1);
		setTitle(DataManager.get(getApplicationContext()).getMonster(id).getName());

		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new MonsterDetailPagerAdapter(getSupportFragmentManager(), id);
		viewPager.setAdapter(mAdapter);

		mSlidingTabLayout.setViewPager(viewPager);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
