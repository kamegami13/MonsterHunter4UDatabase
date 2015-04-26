package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.ui.detail.ItemArenaFragment;
import com.daviancorp.android.ui.detail.ItemComponentFragment;
import com.daviancorp.android.ui.detail.ItemDetailFragment;
import com.daviancorp.android.ui.detail.ItemLocationFragment;
import com.daviancorp.android.ui.detail.ItemMonsterFragment;
import com.daviancorp.android.ui.detail.ItemQuestFragment;
import com.daviancorp.android.ui.detail.ItemTradeFragment;
import com.daviancorp.android.ui.list.CombiningListFragment;

public class ItemDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long itemId;

    // Tab titles
    // TODO: Reenable arena quest tab
    private String[] tabs = { "Detail", "Combining", "Usage", "Monster", "Quest", "Location", "Trade"};

	public ItemDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.itemId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Item detail
			return ItemDetailFragment.newInstance(itemId);
        case 1:
            return CombiningListFragment.newInstance(itemId);
		case 2:
			// List of Armor, Decoration, and Weapon the Item can be used for
			return ItemComponentFragment.newInstance(itemId);
		case 3:
			// Monster drops
			return ItemMonsterFragment.newInstance(itemId);
		case 4:
			// Quest rewards
			return ItemQuestFragment.newInstance(itemId);
		case 5:
			// Location drops; gathering
			return ItemLocationFragment.newInstance(itemId);
        case 6:
            // Wyporium trade info
            return ItemTradeFragment.newInstance(itemId);
//		case 5:
//			// ArenaQuest rewards
//            //TODO reenable when arena quests are complete.
//			return ItemArenaFragment.newInstance(itemId);
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