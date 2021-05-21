package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.Menu;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.QuestDetailPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class QuestDetailActivity extends GenericTabActivity {
    /**
     * A key for passing a quest ID as a long
     */
    public static final String EXTRA_QUEST_ID =
            "com.daviancorp.android.android.ui.detail.monster_id";

    private ViewPager viewPager;
    private QuestDetailPagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = getIntent().getLongExtra(EXTRA_QUEST_ID, -1);
        setTitle(DataManager.get(getApplicationContext()).getQuest(id).getName());

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new QuestDetailPagerAdapter(getSupportFragmentManager(), id);
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.QUESTS;
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
