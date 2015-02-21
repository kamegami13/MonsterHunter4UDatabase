package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.loader.ItemToSkillTreeListCursorLoader;
import com.daviancorp.android.ui.detail.SkillTreeArmorFragment;
import com.daviancorp.android.ui.detail.SkillTreeDecorationFragment;
import com.daviancorp.android.ui.detail.SkillTreeDetailFragment;

public class SkillTreeDetailPagerAdapter extends FragmentPagerAdapter {
	
	private long skillTreeId;

	public SkillTreeDetailPagerAdapter(FragmentManager fm, long id) {
		super(fm);
		this.skillTreeId = id;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// SkillTree detail
			return SkillTreeDetailFragment.newInstance(skillTreeId);
		case 1:
			// List of "Head" Armor with the SkillTree
			return SkillTreeArmorFragment.newInstance(skillTreeId, 
					ItemToSkillTreeListCursorLoader.TYPE_HEAD);
		case 2:
			// List of "Body" Armor with the SkillTree
			return SkillTreeArmorFragment.newInstance(skillTreeId,  
					ItemToSkillTreeListCursorLoader.TYPE_BODY);
		case 3:
			// List of "Arms" Armor with the SkillTree
			return SkillTreeArmorFragment.newInstance(skillTreeId,  
					ItemToSkillTreeListCursorLoader.TYPE_ARMS);
		case 4:
			// List of "Waist" Armor with the SkillTree
			return SkillTreeArmorFragment.newInstance(skillTreeId,  
					ItemToSkillTreeListCursorLoader.TYPE_WAIST);
		case 5:
			// List of "Legs" Armor with the SkillTree
			return SkillTreeArmorFragment.newInstance(skillTreeId,  
					ItemToSkillTreeListCursorLoader.TYPE_LEGS);
		case 6:
			// List of Decoration with the SkillTree
			return SkillTreeDecorationFragment.newInstance(skillTreeId,
					ItemToSkillTreeListCursorLoader.TYPE_DECORATION);
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 7;
	}

}