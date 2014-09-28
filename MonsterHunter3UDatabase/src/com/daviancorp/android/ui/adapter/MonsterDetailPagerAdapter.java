package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.MonsterDetailFragment;
import com.daviancorp.android.ui.detail.MonsterQuestFragment;
import com.daviancorp.android.ui.detail.MonsterRewardFragment;

public class MonsterDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long monsterId;

	public MonsterDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.monsterId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return MonsterDetailFragment.newInstance(monsterId);
		case 1:
			return MonsterRewardFragment.newInstance(monsterId, "LR");
		case 2:
			return MonsterRewardFragment.newInstance(monsterId, "HR");
		case 3:
			return MonsterRewardFragment.newInstance(monsterId, "G");
		case 4:
			return MonsterQuestFragment.newInstance(monsterId);
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}