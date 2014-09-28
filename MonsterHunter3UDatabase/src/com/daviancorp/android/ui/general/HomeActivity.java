package com.daviancorp.android.ui.general;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.actionbarsherlock.view.MenuItem;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.dialog.AboutDialogFragment;

@SuppressLint("NewApi")
public class HomeActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
	}

	@Override
	protected Fragment createFragment() {
		super.detail = new HomeFragment();
		return super.detail;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("helpme", "test");
		switch (item.getItemId()) {
		case android.R.id.home:
			return true;
		case R.id.about:
			Log.d("helpme", "aboutt");
			FragmentManager fm = getSupportFragmentManager();
			AboutDialogFragment dialog = new AboutDialogFragment();
			dialog.show(fm, DIALOG_ABOUT);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
