package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ItemDetailPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class ItemDetailActivity extends GenericTabActivity {

    public static final String EXTRA_ITEM_ID =
            "com.daviancorp.android.android.ui.detail.item_id";

    private ViewPager viewPager;
    private ItemDetailPagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getIntent().getLongExtra(EXTRA_ITEM_ID, -1);
        setTitle(DataManager.get(getApplicationContext()).getItem(id).getName());

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ItemDetailPagerAdapter(getSupportFragmentManager(), id);
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ITEMS;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
