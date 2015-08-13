package com.daviancorp.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.ui.detail.ASBFragment;
import com.daviancorp.android.ui.list.ASBSkillsListFragment;

/**
 * Well that name is a mouthful.
 */
public class ASBPagerAdapter extends FragmentPagerAdapter {

    private ASBSession session;

    private String[] tabs = {"Equipment", "Skills"};

    public ASBPagerAdapter(FragmentManager fm, ASBSession session) {
        super(fm);
        this.session = session;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                // Main builder tab
                return ASBFragment.newInstance(session.getRank(), session.getHunterType());
            case 1:
                // Skills view tab
                return ASBSkillsListFragment.newInstance();
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
