package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.general.GenericTabActivity;

public class WishlistListActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.wishlist);

        // Enable drawer button instead of back button
        super.enableDrawerIndicator();

		/*FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null) {
			fragment = new WishlistListFragment();
			fm.beginTransaction().add(R.id.fragment_container, fragment)
					.commit();
		}*/
	}

    @Override
    protected Fragment createFragment() {
        super.detail = new WishlistListFragment();
        return super.detail;
    }

}
