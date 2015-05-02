package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.MonsterGridPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class MonsterListActivity extends GenericTabActivity {

    private ViewPager viewPager;
    private MonsterGridPagerAdapter mAdapter;
    private int toggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.monsters);
        toggle = 0;

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new MonsterGridPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

        // Enable drawer button instead of back button
        super.enableDrawerIndicator();

        // Tag as top level activity
        super.setAsTopLevel();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.MONSTERS;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.menu_monster_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//		Intent intent;

        switch (item.getItemId()) {
//		case R.id.monster_listview:
//			toggle = 1;
//			intent = new Intent(this, MonsterListActivity.class);
//			startActivity(intent);
//			return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (toggle == 1) {
            finish();
        }
    }

}
