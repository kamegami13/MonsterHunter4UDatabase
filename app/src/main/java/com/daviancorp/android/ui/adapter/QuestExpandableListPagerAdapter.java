package com.daviancorp.android.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.daviancorp.android.loader.QuestListCursorLoader;
import com.daviancorp.android.ui.list.QuestExpandableListFragment;

public class QuestExpandableListPagerAdapter extends FragmentPagerAdapter {

    // TODO reenable when dlc quests are complete
    private String[] tabs = {
            QuestListCursorLoader.HUB_CARAVAN,
            QuestListCursorLoader.HUB_GUILD,
            QuestListCursorLoader.HUB_EVENT};

	public QuestExpandableListPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Village Quests
			return QuestExpandableListFragment.newInstance("Caravan");
		case 1:
			// Port Quests
			return QuestExpandableListFragment.newInstance("Guild");
		case 2:
			// DLC Quests
            //TODO reenable when DLC quests are complete.
			return QuestExpandableListFragment.newInstance("Event");
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