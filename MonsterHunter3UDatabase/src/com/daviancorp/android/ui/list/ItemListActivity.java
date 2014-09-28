package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.view.MenuItem;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;

public class ItemListActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.items);
	}

	@Override
	protected Fragment createFragment() {
		super.detail = new ItemListFragment();
		return super.detail;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

//		Fragment newFragment;
//		FragmentTransaction transaction;

		switch (item.getItemId()) {
//		case R.id.about:
//			
//			newFragment = QuestExpandableListFragment.newInstance("DLC");
//			transaction = getSupportFragmentManager().beginTransaction();
//
//			transaction.replace(R.id.fragment_container, newFragment);
//			// transaction.addToBackStack(null);
//
//			// Commit the transaction
//			transaction.commit();
//
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
