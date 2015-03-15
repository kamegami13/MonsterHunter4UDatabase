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
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ArmorSetBuilderPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.ArmorListActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

import java.util.ArrayList;
import java.util.List;

public class ArmorSetBuilderActivity extends GenericTabActivity {
    public static final String EXTRA_FROM_SET_BUILDER = "com.daviancorp.android.ui.detail.from_set_builder";
    public static final String EXTRA_REMAINING_SOCKETS = "com.daviancorp.android.ui.detail.remaining_sockets";
    public static final String EXTRA_PIECE_INDEX = "com.daviancorp.android.ui.detail.piece_index";
    public static final String EXTRA_DECORATION_INDEX = "com.daviancorp.android.ui.detail.decoration_index";

    public static final int BUILDER_REQUEST_CODE = 537;
    public static final int REMOVE_DECORATION_REQUEST_CODE = 538;

    private ArmorSetBuilderSession session;

    private List<ArmorSetChangedListener> armorSetChangedListeners;

    private ViewPager viewPager;
    private ArmorSetBuilderPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new ArmorSetBuilderSession();

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ArmorSetBuilderPagerAdapter(getSupportFragmentManager(), session);
        viewPager.setAdapter(adapter);

        mSlidingTabLayout.setViewPager(viewPager);

        armorSetChangedListeners = new ArrayList<>();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.ARMOR;
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

                startActivityForResult(intent, BUILDER_REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // If the user canceled the request, we don't want to do anything.
            if (requestCode == BUILDER_REQUEST_CODE) {
                long armorId = data.getLongExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, -1);

                if (armorId != -1) {
                    String armorType = DataManager.get(getApplicationContext()).getArmor(armorId).getSlot();

                    switch (armorType) {
                        case "Head":
                            Log.d("SetBuilder", "Setting head piece.");
                            session.setArmor(ArmorSetBuilderSession.HEAD, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                        case "Body":
                            Log.d("SetBuilder", "Setting body piece.");
                            session.setArmor(ArmorSetBuilderSession.BODY, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                        case "Arms":
                            Log.d("SetBuilder", "Setting arms piece.");
                            session.setArmor(ArmorSetBuilderSession.ARMS, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                        case "Waist":
                            Log.d("SetBuilder", "Setting waist piece.");
                            session.setArmor(ArmorSetBuilderSession.WAIST, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                        case "Legs":
                            Log.d("SetBuilder", "Setting legs piece.");
                            session.setArmor(ArmorSetBuilderSession.LEGS, DataManager.get(getApplicationContext()).getArmor(armorId));
                            break;
                    }
                }

                long decorationId = data.getLongExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, -1);
                int pieceIndex = data.getIntExtra(EXTRA_PIECE_INDEX, -1);

                Log.d("SetBuilder", "Decoration: " + decorationId);
                Log.d("SetBuilder", "Piece Index: " + pieceIndex);

                if (decorationId != -1 && pieceIndex != -1) {
                    Log.d("SetBuilder", "Attempting to add decoration: " + DataManager.get(this).getDecoration(decorationId).getName());

                    Decoration decoration = DataManager.get(this).getDecoration(decorationId);

                    if (!session.addDecoration(pieceIndex, decoration)) {
                        Log.i("SetBuilder", "Couldn't add decoration.");
                    }
                }

            }
            else if (requestCode == REMOVE_DECORATION_REQUEST_CODE) {
                int pieceIndex = data.getIntExtra(EXTRA_PIECE_INDEX, -1);
                int decorationIndex = data.getIntExtra(EXTRA_DECORATION_INDEX, -1);

                Log.d("SetBuilder", "Attempting to remove decoration at index: " + pieceIndex + "," + decorationIndex);

                session.removeDecoration(pieceIndex, decorationIndex);
            }

            for (ArmorSetChangedListener a : armorSetChangedListeners) {
                a.updateContents(session);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public ArmorSetBuilderSession getArmorSetBuilderSession() {
        return session;
    }

    public void addArmorSetChangedListener(ArmorSetChangedListener a) {
        armorSetChangedListeners.add(a);
    }

    public void fragmentResultReceived(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    public static interface ArmorSetChangedListener {
        public void updateContents(ArmorSetBuilderSession s);
    }
}
