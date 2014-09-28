package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.ArenaQuestDetailFragment;
import com.daviancorp.android.ui.detail.ArenaQuestMonsterFragment;
import com.daviancorp.android.ui.detail.ArenaQuestRewardFragment;

public class ArenaQuestDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long arenaQuestId;

	public ArenaQuestDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.arenaQuestId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return ArenaQuestDetailFragment.newInstance(arenaQuestId);
		case 1:
			return ArenaQuestMonsterFragment.newInstance(arenaQuestId);
		case 2:
			return ArenaQuestRewardFragment.newInstance(arenaQuestId);

		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}