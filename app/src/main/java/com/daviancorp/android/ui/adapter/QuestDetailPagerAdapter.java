package com.daviancorp.android.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.QuestDetailFragment;
import com.daviancorp.android.ui.detail.QuestMonsterFragment;
import com.daviancorp.android.ui.detail.QuestRewardFragment;

public class QuestDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long questId;

    // Tab titles
    private String[] tabs = { "Detail", "Monsters", "Rewards"};

	public QuestDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.questId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Quest detail
			return QuestDetailFragment.newInstance(questId);
		case 1:
			// Monsters in Quest
			return QuestMonsterFragment.newInstance(questId);
		case 2:
			// Quest rewards
			return QuestRewardFragment.newInstance(questId);
		default:
			return null;
		}
	}

    @Override
    public CharSequence getPageTitle(int index) {
        return tabs[index];
    }

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}