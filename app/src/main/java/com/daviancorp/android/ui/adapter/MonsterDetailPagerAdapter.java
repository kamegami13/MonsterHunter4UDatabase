package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.loader.HuntingRewardListCursorLoader;
import com.daviancorp.android.ui.detail.MonsterDamageFragment;
import com.daviancorp.android.ui.detail.MonsterHabitatFragment;
import com.daviancorp.android.ui.detail.MonsterQuestFragment;
import com.daviancorp.android.ui.detail.MonsterRewardFragment;
import com.daviancorp.android.ui.detail.MonsterStatusFragment;

public class MonsterDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long monsterId;

	public MonsterDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.monsterId = id;
	}

    // Tab titles
    private String[] tabs = { "Damage","Status","Habitat","Low-Rank", "High-Rank", "G-Rank", "Quest"};

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Monster Damage
			return MonsterDamageFragment.newInstance(monsterId);
        case 1:
            // Monster Status
            return MonsterStatusFragment.newInstance(monsterId);
        case 2:
            // Monster Habitat
            return MonsterHabitatFragment.newInstance(monsterId);
		case 3:
			// Low-rank drops
			return MonsterRewardFragment.newInstance(monsterId, HuntingRewardListCursorLoader.RANK_LR);
		case 4:
			// High-rank drops
			return MonsterRewardFragment.newInstance(monsterId, HuntingRewardListCursorLoader.RANK_HR);
		case 5:
			// G-rank drops
			return MonsterRewardFragment.newInstance(monsterId, HuntingRewardListCursorLoader.RANK_G);
		case 6:
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
		return 7;
	}

}