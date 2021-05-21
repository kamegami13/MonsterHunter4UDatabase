package com.daviancorp.android.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.daviancorp.android.loader.HuntingRewardListCursorLoader;
import com.daviancorp.android.ui.detail.MonsterDamageFragment;
import com.daviancorp.android.ui.detail.MonsterHabitatFragment;
import com.daviancorp.android.ui.detail.MonsterQuestFragment;
import com.daviancorp.android.ui.detail.MonsterRewardFragment;
import com.daviancorp.android.ui.detail.MonsterStatusFragment;
import com.daviancorp.android.ui.detail.MonsterSummaryFragment;

public class MonsterDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long monsterId;

	public MonsterDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.monsterId = id;
	}

    // Tab titles
    private String[] tabs = { "Summary", "Damage","Status","Habitat","Low-Rank", "High-Rank", "G-Rank", "Quest"};

	@Override
	public Fragment getItem(int index) {

		switch (index) {
			case 0:
				// Monster Summary
				return MonsterSummaryFragment.newInstance(monsterId);
			case 1:
				// Monster Damage
				return MonsterDamageFragment.newInstance(monsterId);
			case 2:
				// Monster Status
				return MonsterStatusFragment.newInstance(monsterId);
			case 3:
				// Monster Habitat
				return MonsterHabitatFragment.newInstance(monsterId);
			case 4:
				// Low-rank drops
				return MonsterRewardFragment.newInstance(monsterId, HuntingRewardListCursorLoader.RANK_LR);
			case 5:
				// High-rank drops
				return MonsterRewardFragment.newInstance(monsterId, HuntingRewardListCursorLoader.RANK_HR);
			case 6:
				// G-rank drops
				return MonsterRewardFragment.newInstance(monsterId, HuntingRewardListCursorLoader.RANK_G);
			case 7:
				// Quest appearance
				return MonsterQuestFragment.newInstance(monsterId);
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
		return 8;
	}

}