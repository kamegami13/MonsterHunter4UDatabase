package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
			return SkillTreeDetailFragment.newInstance(skillTreeId);
		case 1:
			return SkillTreeArmorFragment.newInstance(skillTreeId, "Head");
		case 2:
			return SkillTreeArmorFragment.newInstance(skillTreeId, "Body");
		case 3:
			return SkillTreeArmorFragment.newInstance(skillTreeId, "Arms");
		case 4:
			return SkillTreeArmorFragment.newInstance(skillTreeId, "Waist");
		case 5:
			return SkillTreeArmorFragment.newInstance(skillTreeId, "Legs");
		case 6:
			return SkillTreeDecorationFragment.newInstance(skillTreeId, "Decoration");
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