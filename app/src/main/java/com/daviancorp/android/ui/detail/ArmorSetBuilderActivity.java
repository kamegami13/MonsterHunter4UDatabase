package com.daviancorp.android.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.data.classes.Decoration;
import com.daviancorp.android.data.classes.SkillTree;
import com.daviancorp.android.data.classes.Talisman;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ArmorSetBuilderPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.ArmorListActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

import java.util.ArrayList;
import java.util.List;

public class ArmorSetBuilderActivity extends GenericTabActivity implements ArmorSetBuilderSession.OnArmorSetChangedListener {

    public static final String EXTRA_FROM_SET_BUILDER = "com.daviancorp.android.ui.detail.from_set_builder";
    public static final String EXTRA_FROM_TALISMAN_EDITOR = "com.daviancorp.android.ui.detail.from_talisman_editor";
    public static final String EXTRA_TALISMAN_SKILL_INDEX = "com.daviancorp.android.ui.detail.talisman_skill_number";
    public static final String EXTRA_PIECE_INDEX = "com.daviancorp.android.ui.detail.piece_index";
    public static final String EXTRA_DECORATION_INDEX = "com.daviancorp.android.ui.detail.decoration_index";

    public static final String EXTRA_TALISMAN_SKILL_TREE_1 = "com.daviancorp.android.ui.detail.skill_tree_1";
    public static final String EXTRA_TALISMAN_SKILL_POINTS_1 = "com.daviancorp.android.ui.detail.skill_points_1";
    public static final String EXTRA_TALISMAN_SKILL_TREE_2 = "com.daviancorp.android.ui.detail.skill_tree_2";
    public static final String EXTRA_TALISMAN_SKILL_POINTS_2 = "com.daviancorp.android.ui.detail.skill_points_2";
    public static final String EXTRA_TALISMAN_TYPE_INDEX = "com.daviancorp.android.ui.detail.talisman_type_index";

    public static final int REQUEST_CODE_ADD_PIECE = 537;
    public static final int REQUEST_CODE_ADD_DECORATION = 538;
    public static final int REQUEST_CODE_CREATE_TALISMAN = 539;

    private ArmorSetBuilderSession session;

    private List<OnArmorSetActivityUpdateListener> onArmorSetActivityUpdateListeners;

    private ViewPager viewPager;
    private ArmorSetBuilderPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.activity_armor_set_builder);

        session = new ArmorSetBuilderSession();
        session.addOnArmorSetChangedListener(this);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ArmorSetBuilderPagerAdapter(getSupportFragmentManager(), session);
        viewPager.setAdapter(adapter);

        mSlidingTabLayout.setViewPager(viewPager);

        onArmorSetActivityUpdateListeners = new ArrayList<>();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ARMOR_SET_BUILDER;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_set_builder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_builder_add_piece: // The user wants to add an armor piece
                Intent intent = new Intent(getApplicationContext(), ArmorListActivity.class);
                intent.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER, true);

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
                            Log.d("SetBuilder", "Setting head piece.");
                            session.setEquipment(ArmorSetBuilderSession.HEAD, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                        case "Body":
                            Log.d("SetBuilder", "Setting body piece.");
                            session.setEquipment(ArmorSetBuilderSession.BODY, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                        case "Arms":
                            Log.d("SetBuilder", "Setting arms piece.");
                            session.setEquipment(ArmorSetBuilderSession.ARMS, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                        case "Waist":
                            Log.d("SetBuilder", "Setting waist piece.");
                            session.setEquipment(ArmorSetBuilderSession.WAIST, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                        case "Legs":
                            Log.d("SetBuilder", "Setting legs piece.");
                            session.setEquipment(ArmorSetBuilderSession.LEGS, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                    }
                }
            }
            else if (requestCode == REQUEST_CODE_ADD_DECORATION) {
                long decorationId = data.getLongExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, -1);
                int pieceIndex = data.getIntExtra(EXTRA_PIECE_INDEX, -1);

                if (decorationId != -1 && pieceIndex != -1) {
                    Decoration decoration = DataManager.get(this).getDecoration(decorationId);

                    if (!session.addDecoration(pieceIndex, decoration)) {
                        Log.e("SetBuilder", "A decoration was attempted to be added, but it failed.");
                    }
                }
            }
            else if (requestCode == REQUEST_CODE_CREATE_TALISMAN) {
                Talisman talisman;

                long skill1Id = data.getLongExtra(EXTRA_TALISMAN_SKILL_TREE_1, -1);
                int skill1Points = data.getIntExtra(EXTRA_TALISMAN_SKILL_POINTS_1, -1);
                int typeIndex = data.getIntExtra(EXTRA_TALISMAN_TYPE_INDEX, -1);

                SkillTree skill1Tree = DataManager.get(getApplicationContext()).getSkillTree(skill1Id);
                talisman = new Talisman(skill1Tree, skill1Points, typeIndex);

                if (data.hasExtra(EXTRA_TALISMAN_SKILL_TREE_2)) {
                    long skill2Id = data.getLongExtra(EXTRA_TALISMAN_SKILL_TREE_2, -1);
                    int skill2Points = data.getIntExtra(EXTRA_TALISMAN_SKILL_POINTS_2, -1);

                    SkillTree skill2Tree = DataManager.get(getApplicationContext()).getSkillTree(skill2Id);
                    talisman.setSecondSkill(skill2Tree, skill2Points);
                }

                session.setEquipment(ArmorSetBuilderSession.TALISMAN, talisman);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onArmorSetChanged() {
        session.updateSkillTreePointsSets(this);

        for (OnArmorSetActivityUpdateListener a : onArmorSetActivityUpdateListeners) {
            a.onArmorSetActivityUpdated(session);
        }
    }

    public ArmorSetBuilderSession getArmorSetBuilderSession() {
        return session;
    }

    /** To be called when a fragment contained within this activity has {@code onActivityResult} manually called on it. */
    public void fragmentResultReceived(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    public void addArmorSetChangedListener(OnArmorSetActivityUpdateListener a) {
        onArmorSetActivityUpdateListeners.add(a);
    }

    public static interface OnArmorSetActivityUpdateListener {
        public void onArmorSetActivityUpdated(ArmorSetBuilderSession s);
    }
}
