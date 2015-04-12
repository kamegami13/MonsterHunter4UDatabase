package com.daviancorp.android.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.data.classes.Decoration;
import com.daviancorp.android.data.classes.SkillTree;
import com.daviancorp.android.data.classes.ASBTalisman;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.loader.ASBSetLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ASBPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.ASBSetListFragment;
import com.daviancorp.android.ui.list.ArmorListActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

import java.util.ArrayList;
import java.util.List;

public class ASBActivity extends GenericTabActivity implements ASBSession.OnASBSetChangedListener {

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

    public static final int REQUEST_CODE_ADD_PIECE = 0;
    public static final int REQUEST_CODE_ADD_DECORATION = 1;
    public static final int REQUEST_CODE_CREATE_TALISMAN = 2;

    private ASBSession session;

    private List<OnASBSetActivityUpdateListener> onASBSetActivityUpdateListeners;

    private ViewPager viewPager;
    private ASBPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long setId = getIntent().getLongExtra(ASBSetListFragment.EXTRA_ASB_SET_ID, -1);
        setTitle(DataManager.get(this).getASBSet(setId).getName());

        viewPager = (ViewPager) findViewById(R.id.pager);

        onASBSetActivityUpdateListeners = new ArrayList<>();

        LoaderManager lm = getSupportLoaderManager();
        lm.initLoader(R.id.asb_set_activity, null, new ASBSetLoaderCallbacks());
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ARMOR_SET_BUILDER;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_asb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_builder_add_piece: // The user wants to add an armor piece
                Intent intent = new Intent(getApplicationContext(), ArmorListActivity.class);
                intent.putExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, true);

                startActivityForResult(intent, REQUEST_CODE_ADD_PIECE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // If the user canceled the request, we don't want to do anything.
            if (requestCode == REQUEST_CODE_ADD_PIECE) {
                long armorId = data.getLongExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, -1);

                if (armorId != -1) {
                    String armorType = DataManager.get(getApplicationContext()).getArmor(armorId).getSlot();

                    switch (armorType) {
                        case "Head":
                            session.setEquipment(ASBSession.HEAD, DataManager.get(getApplicationContext()).getArmor(armorId));
                            DataManager.get(this).queryPutASBSetArmor(session.getId(), armorId, ASBSession.HEAD);
                            break;
                        case "Body":
                            session.setEquipment(ASBSession.BODY, DataManager.get(getApplicationContext()).getArmor(armorId));
                            DataManager.get(this).queryPutASBSetArmor(session.getId(), armorId, ASBSession.BODY);
                            break;
                        case "Arms":
                            session.setEquipment(ASBSession.ARMS, DataManager.get(getApplicationContext()).getArmor(armorId));
                            DataManager.get(this).queryPutASBSetArmor(session.getId(), armorId, ASBSession.ARMS);
                            break;
                        case "Waist":
                            session.setEquipment(ASBSession.WAIST, DataManager.get(getApplicationContext()).getArmor(armorId));
                            DataManager.get(this).queryPutASBSetArmor(session.getId(), armorId, ASBSession.WAIST);
                            break;
                        case "Legs":
                            session.setEquipment(ASBSession.LEGS, DataManager.get(getApplicationContext()).getArmor(armorId));
                            DataManager.get(this).queryPutASBSetArmor(session.getId(), armorId, ASBSession.LEGS);
                            break;
                    }
                }
            }
            else if (requestCode == REQUEST_CODE_ADD_DECORATION) {
                long decorationId = data.getLongExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, -1);
                int pieceIndex = data.getIntExtra(EXTRA_PIECE_INDEX, -1);

                if (decorationId != -1 && pieceIndex != -1) {
                    Decoration decoration = DataManager.get(this).getDecoration(decorationId);

                    session.addDecoration(pieceIndex, decoration);
                }
            }
            else if (requestCode == REQUEST_CODE_CREATE_TALISMAN) {
                ASBTalisman talisman;

                int typeIndex = data.getIntExtra(EXTRA_TALISMAN_TYPE_INDEX, -1);
                int slots = data.getIntExtra(EXTRA_TALISMAN_SLOTS, 0);

                long skill1Id = data.getLongExtra(EXTRA_TALISMAN_SKILL_TREE_1, -1);
                int skill1Points = data.getIntExtra(EXTRA_TALISMAN_SKILL_POINTS_1, -1);

                long skill2Id = -1;
                int skill2Points = 0;

                SkillTree skill1Tree = DataManager.get(getApplicationContext()).getSkillTree(skill1Id);
                talisman = new ASBTalisman(skill1Tree, skill1Points, typeIndex);
                talisman.setNumSlots(slots);

                if (data.hasExtra(EXTRA_TALISMAN_SKILL_TREE_2)) {
                    skill2Id = data.getLongExtra(EXTRA_TALISMAN_SKILL_TREE_2, -1);
                    skill2Points = data.getIntExtra(EXTRA_TALISMAN_SKILL_POINTS_2, -1);

                    SkillTree skill2Tree = DataManager.get(getApplicationContext()).getSkillTree(skill2Id);
                    talisman.setSkill2(skill2Tree);
                    talisman.setSkill2Points(skill2Points);
                }

                DataManager.get(this).queryCreateASBSetTalisman(session.getId(), typeIndex, slots, skill1Id, skill1Points, skill2Id, skill2Points);

                session.setEquipment(ASBSession.TALISMAN, talisman);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onASBSetChanged() {
        session.updateSkillTreePointsSets(this);

        if (onASBSetActivityUpdateListeners != null) {
            for (OnASBSetActivityUpdateListener a : onASBSetActivityUpdateListeners) {
                a.onASBActivityUpdated(session);
            }
        }
    }

    @Override
    public void onASBSetChanged(int pieceIndex) {
        session.updateSkillTreePointsSets(this);

        if (onASBSetActivityUpdateListeners != null) {
            for (OnASBSetActivityUpdateListener a : onASBSetActivityUpdateListeners) {
                a.onASBActivityUpdated(session, pieceIndex);
            }
        }
    }

    public ASBSession getASBSession() {
        return session;
    }

    /** To be called when a fragment contained within this activity has {@code onActivityResult} manually called on it. */
    public void fragmentResultReceived(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    public void addASBSetChangedListener(OnASBSetActivityUpdateListener a) {
        onASBSetActivityUpdateListeners.add(a);
    }

    public interface OnASBSetActivityUpdateListener {
        void onASBActivityUpdated(ASBSession s);

        void onASBActivityUpdated(ASBSession s, int pieceIndex);
    }

    private class ASBSetLoaderCallbacks implements LoaderManager.LoaderCallbacks<ASBSession> {

        @Override
        public Loader<ASBSession> onCreateLoader(int id, Bundle args) {
            return new ASBSetLoader(ASBActivity.this, getIntent().getLongExtra(ASBSetListFragment.EXTRA_ASB_SET_ID, -1));
        }

        @Override
        public void onLoadFinished(Loader<ASBSession> loader, ASBSession run) {
            session = run;
            session.addOnASBSetChangedListener(ASBActivity.this);
            setTitle(session.getName());

            // Initialization
            adapter = new ASBPagerAdapter(getSupportFragmentManager(), session);
            viewPager.setAdapter(adapter);
            mSlidingTabLayout.setViewPager(viewPager);

            onASBSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<ASBSession> loader) {
            // Do nothing
        }
    }
}
