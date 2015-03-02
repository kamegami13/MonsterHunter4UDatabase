package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.WishlistDataComponentFragment;
import com.daviancorp.android.ui.detail.WishlistDataDetailFragment;

public class WishlistDetailPagerAdapter extends FragmentPagerAdapter {
    // Tab titles
    private String[] tabs = { "Wishlist" , "Materials"};

	private static final int REQUEST_REFRESH = 0;
	
	private long wishlistId;
	private WishlistDataDetailFragment mWishlistDataDetailFragment;
	private WishlistDataComponentFragment mWishlistDataComponentFragment;

	public WishlistDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.wishlistId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// WishlistData
			mWishlistDataDetailFragment = WishlistDataDetailFragment.newInstance(wishlistId);
			
			// For refreshing each other
			if (mWishlistDataComponentFragment != null) {
				mWishlistDataDetailFragment.setTargetFragment(mWishlistDataComponentFragment, REQUEST_REFRESH);
				mWishlistDataComponentFragment.setTargetFragment(mWishlistDataDetailFragment, REQUEST_REFRESH);
			}
			
			return mWishlistDataDetailFragment;
		case 1:
			// WistlistComponent
			mWishlistDataComponentFragment = WishlistDataComponentFragment.newInstance(wishlistId);
			
			// For refreshing each other
			if (mWishlistDataDetailFragment != null) {
				mWishlistDataDetailFragment.setTargetFragment(mWishlistDataComponentFragment, REQUEST_REFRESH);
				mWishlistDataComponentFragment.setTargetFragment(mWishlistDataDetailFragment, REQUEST_REFRESH);
			}
			
			return mWishlistDataComponentFragment;
		default:
			return null;
		}
	}

    @Override
    public CharSequence getPageTitle(int index) {
        return tabs[index];
    }

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}