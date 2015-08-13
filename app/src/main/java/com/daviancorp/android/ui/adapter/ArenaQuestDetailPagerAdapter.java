package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.ArenaQuestDetailFragment;
import com.daviancorp.android.ui.detail.ArenaQuestMonsterFragment;
import com.daviancorp.android.ui.detail.ArenaQuestRewardFragment;

public class ArenaQuestDetailPagerAdapter extends FragmentPagerAdapter {

    private long arenaQuestId;

    // Tab titles
    private String[] tabs = {"Detail", "Monsters", "Rewards"};

    public ArenaQuestDetailPagerAdapter(FragmentManager fm, long id) {
        super(fm);
        this.arenaQuestId = id;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // ArenaQuest detail
                return ArenaQuestDetailFragment.newInstance(arenaQuestId);
            case 1:
                // Monsters in ArenaQuest
                return ArenaQuestMonsterFragment.newInstance(arenaQuestId);
            case 2:
                // Item rewards in ArenaQuest
                return ArenaQuestRewardFragment.newInstance(arenaQuestId);

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