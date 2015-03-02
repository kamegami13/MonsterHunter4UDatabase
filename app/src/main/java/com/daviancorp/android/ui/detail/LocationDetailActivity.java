package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.LocationDetailPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class LocationDetailActivity extends GenericTabActivity {
    /**
     * A key for passing a monster ID as a long
     */
    public static final String EXTRA_LOCATION_ID =
            "com.daviancorp.android.android.ui.detail.location_id";

    private ViewPager viewPager;
    private LocationDetailPagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = getIntent().getLongExtra(EXTRA_LOCATION_ID, -1);
        setTitle(DataManager.get(getApplicationContext()).getLocation(id).getName());

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new LocationDetailPagerAdapter(getSupportFragmentManager(), id);
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.LOCATIONS;
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
