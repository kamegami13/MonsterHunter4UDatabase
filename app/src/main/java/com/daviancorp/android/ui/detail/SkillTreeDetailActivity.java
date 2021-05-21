package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import android.view.Menu;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.SkillTreeDetailPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class SkillTreeDetailActivity extends GenericTabActivity {
    /**
     * A key for passing a monster ID as a long
     */
    public static final String EXTRA_SKILLTREE_ID =
            "com.daviancorp.android.android.ui.detail.skill_id";

    private ViewPager viewPager;
    private SkillTreeDetailPagerAdapter mAdapter;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = getIntent().getLongExtra(EXTRA_SKILLTREE_ID, -1);
        setTitle(DataManager.get(getApplicationContext()).getSkillTree(id).getName());

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new SkillTreeDetailPagerAdapter(getSupportFragmentManager(), id);
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.SKILL_TREES;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
