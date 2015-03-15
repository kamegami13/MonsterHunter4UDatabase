package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.ui.detail.ArmorSetBuilderFragment;
import com.daviancorp.android.ui.list.ArmorSetBuilderSkillsListFragment;

/**
 * Well that name is a mouthful.
 */
public class ArmorSetBuilderPagerAdapter extends FragmentPagerAdapter {

    private ArmorSetBuilderSession session;

    private String[] tabs = {"Pieces", "Skills"};

    public ArmorSetBuilderPagerAdapter(FragmentManager fm, ArmorSetBuilderSession session) {
        super(fm);
        this.session = session;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                // Main builder tab
                return ArmorSetBuilderFragment.newInstance();
            case 1:
                // Skills view tab
                return ArmorSetBuilderSkillsListFragment.newInstance(session);
            default:
                // Something went wrong oh god
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return 2;
    }

}
