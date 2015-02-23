package com.daviancorp.android.ui.general;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;

/*
 * The home screen activity upon starting the application
 */
@SuppressLint("NewApi")
public class HomeActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(false);
		setTitle(R.string.app_name);

	}

	@Override
	protected Fragment createFragment() {
		super.detail = new HomeFragment();
		return super.detail;
	}
}
