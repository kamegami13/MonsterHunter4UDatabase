package com.daviancorp.android.ui.list;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class WishlistListActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.wishlist);

        // Tag as top level activity
        super.setAsTopLevel();

		/*FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null) {
			fragment = new WishlistListFragment();
			fm.beginTransaction().add(R.id.fragment_container, fragment)
					.commit();
		}*/
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.WISH_LISTS;
    }

    @Override
    protected Fragment createFragment() {
        super.detail = new WishlistListFragment();
        return super.detail;
    }

}
