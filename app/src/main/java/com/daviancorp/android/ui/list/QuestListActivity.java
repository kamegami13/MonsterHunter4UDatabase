package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.QuestExpandableListPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class QuestListActivity extends GenericTabActivity {

    private ViewPager viewPager;
    private QuestExpandableListPagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.quests);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new QuestExpandableListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

        // Tag as top level activity
        super.setAsTopLevel();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.QUESTS;
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

}
