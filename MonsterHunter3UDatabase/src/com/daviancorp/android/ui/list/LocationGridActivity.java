package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.view.Menu;
import com.daviancorp.android.mh3udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;

public class LocationGridActivity extends GenericActivity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.locations);
	}

	@Override
	protected Fragment createFragment() {
		super.detail = new LocationGridFragment();
		return super.detail;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

}
