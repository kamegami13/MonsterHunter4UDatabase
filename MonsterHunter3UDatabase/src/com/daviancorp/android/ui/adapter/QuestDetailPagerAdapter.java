package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.QuestDetailFragment;
import com.daviancorp.android.ui.detail.QuestMonsterFragment;
import com.daviancorp.android.ui.detail.QuestRewardFragment;

public class QuestDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long questId;

	public QuestDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.questId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return QuestDetailFragment.newInstance(questId);
		case 1:
			return QuestMonsterFragment.newInstance(questId);
		case 2:
			return QuestRewardFragment.newInstance(questId);
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