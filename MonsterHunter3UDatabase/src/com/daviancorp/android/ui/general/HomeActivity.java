package com.daviancorp.android.ui.general;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.view.MenuItem;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.dialog.AboutDialogFragment;

/*
 * The home screen activity upon starting the application
 */
@SuppressLint("NewApi")
public class HomeActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);

	}

	@Override
	protected Fragment createFragment() {
		super.detail = new HomeFragment();
		return super.detail;
	}
}
