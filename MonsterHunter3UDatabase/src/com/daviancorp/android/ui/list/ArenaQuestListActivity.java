package com.daviancorp.android.ui.list;

import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ArenaQuestListActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.arena_quests);
	}

	@Override
	protected Fragment createFragment() {
		super.detail = new ArenaQuestListFragment();
		return super.detail;
	}

}
