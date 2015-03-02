package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.WishlistDetailPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class WishlistDetailActivity extends GenericTabActivity {
    /**
     * A key for passing a wishlist ID as a long
     */
    public static final String EXTRA_WISHLIST_ID =
            "com.daviancorp.android.android.ui.detail.wishlist_id";

    private ViewPager viewPager;
    private WishlistDetailPagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = getIntent().getLongExtra(EXTRA_WISHLIST_ID, -1);
        setTitle(DataManager.get(getApplicationContext()).getWishlist(id).getName());

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new WishlistDetailPagerAdapter(getSupportFragmentManager(), id);
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.WISH_LISTS;
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
