package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class SkillTreeListActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.skill_trees);

        // Enable drawer button instead of back button
        super.enableDrawerIndicator();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.SKILL_TREES;
    }

    @Override
    protected Fragment createFragment() {
        super.detail = new SkillTreeListFragment();
        return super.detail;
    }

}
