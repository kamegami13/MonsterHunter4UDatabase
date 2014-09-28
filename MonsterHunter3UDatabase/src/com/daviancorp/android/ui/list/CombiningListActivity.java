package com.daviancorp.android.ui.list;

import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CombiningListActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.combining);
	}

	@Override
	protected Fragment createFragment() {
		super.detail = new CombiningListFragment();
		return super.detail;
	}

}
