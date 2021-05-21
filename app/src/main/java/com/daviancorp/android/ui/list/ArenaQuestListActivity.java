package com.daviancorp.android.ui.list;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class ArenaQuestListActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.arena_quests);
	}

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.QUESTS;
    }

    @Override
	protected Fragment createFragment() {
		super.detail = new ArenaQuestListFragment();
		return super.detail;
	}

}
