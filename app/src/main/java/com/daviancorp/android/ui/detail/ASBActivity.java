package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.loader.ASBSessionLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ASBPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.ASBSetListFragment;
import com.daviancorp.android.ui.list.adapter.MenuSection;

import java.util.ArrayList;
import java.util.List;

public class ASBActivity extends GenericTabActivity {

    public static final String EXTRA_FROM_SET_BUILDER = "com.daviancorp.android.ui.detail.from_set_builder";
    public static final String EXTRA_FROM_TALISMAN_EDITOR = "com.daviancorp.android.ui.detail.from_talisman_editor";
    public static final String EXTRA_TALISMAN_SKILL_INDEX = "com.daviancorp.android.ui.detail.talisman_skill_number";
    public static final String EXTRA_PIECE_INDEX = "com.daviancorp.android.ui.detail.piece_index";
    public static final String EXTRA_DECORATION_INDEX = "com.daviancorp.android.ui.detail.decoration_index";
    public static final String EXTRA_SET_RANK = "com.daviancorp.android.ui.detail.set_rank";
    public static final String EXTRA_SET_HUNTER_TYPE = "com.daviancorp.android.ui.detail.hunter_type";

    public static final String EXTRA_TALISMAN_SKILL_TREE_1 = "com.daviancorp.android.ui.detail.skill_tree_1";
    public static final String EXTRA_TALISMAN_SKILL_POINTS_1 = "com.daviancorp.android.ui.detail.skill_points_1";
    public static final String EXTRA_TALISMAN_SKILL_TREE_2 = "com.daviancorp.android.ui.detail.skill_tree_2";
    public static final String EXTRA_TALISMAN_SKILL_POINTS_2 = "com.daviancorp.android.ui.detail.skill_points_2";
    public static final String EXTRA_TALISMAN_TYPE_INDEX = "com.daviancorp.android.ui.detail.talisman_type_index";
    public static final String EXTRA_TALISMAN_SLOTS = "com.daviancorp.android.ui.detail.talisman_slots";

    public static final int REQUEST_CODE_ADD_PIECE = 537;
    public static final int REQUEST_CODE_ADD_DECORATION = 538;
    public static final int REQUEST_CODE_CREATE_TALISMAN = 539;
    public static final int REQUEST_CODE_REMOVE_PIECE = 540;
    public static final int REQUEST_CODE_REMOVE_DECORATION = 541;

    private ASBSession session;

    private List<OnASBSetActivityUpdateListener> onASBSetActivityUpdateListeners;

    private ViewPager viewPager;
    private ASBPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager = (ViewPager) findViewById(R.id.pager);

        onASBSetActivityUpdateListeners = new ArrayList<>();

        setTitle(getIntent().getStringExtra(ASBSetListFragment.EXTRA_ASB_SET_NAME));

        LoaderManager lm = getSupportLoaderManager();
        lm.initLoader(R.id.asb_set_activity, null, new ASBSetLoaderCallbacks());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ARMOR_SET_BUILDER;
    }

    public ASBSession getASBSession() {
        return session;
    }

    public void addASBSetChangedListener(OnASBSetActivityUpdateListener a) {
        onASBSetActivityUpdateListeners.add(a);
    }

    public void updateASBSetChangedListeners() {
        if (onASBSetActivityUpdateListeners != null) {
            for (OnASBSetActivityUpdateListener a : onASBSetActivityUpdateListeners) {
                a.onASBActivityUpdated(session);
            }
        }
    }

    public interface OnASBSetActivityUpdateListener {
        void onASBActivityUpdated(ASBSession s);
    }

    private class ASBSetLoaderCallbacks implements LoaderManager.LoaderCallbacks<ASBSession> {
        @Override
        public Loader<ASBSession> onCreateLoader(int id, Bundle args) {
            return new ASBSessionLoader(ASBActivity.this, getIntent().getLongExtra(ASBSetListFragment.EXTRA_ASB_SET_ID, -1));
        }

        @Override
        public void onLoadFinished(Loader<ASBSession> loader, ASBSession run) {
            session = run;

            // Initialization
            adapter = new ASBPagerAdapter(getSupportFragmentManager(), session);
            viewPager.setAdapter(adapter);
            mSlidingTabLayout.setViewPager(viewPager);

            updateASBSetChangedListeners();
        }

        @Override
        public void onLoaderReset(Loader<ASBSession> loader) {
        }
    }
}
