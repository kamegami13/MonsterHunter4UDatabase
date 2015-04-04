package com.daviancorp.android.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ArmorExpandableListPagerAdapter;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class ArmorListActivity extends GenericTabActivity {

    private ViewPager viewPager;
    private ArmorExpandableListPagerAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.armor);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ArmorExpandableListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

        // Enable back button if we're coming from the set builder
        if (getIntent().getBooleanExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER, false)) {
            super.disableDrawerIndicator();
        }
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ARMOR;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ArmorSetBuilderActivity.REQUEST_CODE_ADD_PIECE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
