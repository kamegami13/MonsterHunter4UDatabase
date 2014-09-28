package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.general.GenericTabActivity;

public class WishlistListActivity extends GenericTabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.wishlist);
		
		setContentView(R.layout.activity_main);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null) {
			fragment = new WishlistListFragment();
			fm.beginTransaction().add(R.id.fragment_container, fragment)
					.commit();
		}
	}

}
