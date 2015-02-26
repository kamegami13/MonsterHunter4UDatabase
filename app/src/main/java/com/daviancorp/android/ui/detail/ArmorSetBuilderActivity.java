package com.daviancorp.android.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.ArmorSetBuilderPagerAdapter;
import com.daviancorp.android.ui.general.GenericTabActivity;
import com.daviancorp.android.ui.list.ArmorListActivity;

public class ArmorSetBuilderActivity extends GenericTabActivity implements ActionBar.TabListener {
    public static final String EXTRA_FROM_SET_BUILDER = "com.daviancorp.android.ui.detail.from_set_builder";
    public static final int REQUEST_CODE = 537;

    private ArmorSetBuilderSession session; //ASSSIGN

    private ArmorSetChangedListener changeListener;

    private ViewPager viewPager;
    private ArmorSetBuilderPagerAdapter adapter;
    private ActionBar actionBar;

    private String[] tabs = {"Pieces", "Skills"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.activity_armor_set_builder));

        session = new ArmorSetBuilderSession();

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ArmorSetBuilderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab).setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // On changing the page, make the desired tab the selected one
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
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

                startActivityForResult(intent, REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // If the user canceled the request, we don't want to do anything.
            long id = data.getLongExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, -1);

            if (id != -1) {
                String armorType = DataManager.get(getApplicationContext()).getArmor(id).getSlot();

                switch (armorType) {
                    case "Head":
                        Log.d("SetBuilder", "Setting head piece.");
                        session.setHead(DataManager.get(getApplicationContext()).getArmor(id));
                        break;
                    case "Body":
                        Log.d("SetBuilder", "Setting body piece.");
                        session.setBody(DataManager.get(getApplicationContext()).getArmor(id));
                        break;
                    case "Arms":
                        Log.d("SetBuilder", "Setting arms piece.");
                        session.setArms(DataManager.get(getApplicationContext()).getArmor(id));
                        break;
                    case "Waist":
                        Log.d("SetBuilder", "Setting waist piece.");
                        session.setWaist(DataManager.get(getApplicationContext()).getArmor(id));
                        break;
                    case "Legs":
                        Log.d("SetBuilder", "Setting legs piece.");
                        session.setLegs(DataManager.get(getApplicationContext()).getArmor(id));
                        break;
                    default:
                        Log.e("SetBuilder", "The armor type did not match any of the cases.");
                        break;
                }
            }

            changeListener.updateContents(session);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Obviously, changes to the selected tab when said tab is selected
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    public void setOnArmorSetChangedListener(ArmorSetChangedListener listener) {
        this.changeListener = listener;
    }

    public static interface ArmorSetChangedListener {
        public void updateContents(ArmorSetBuilderSession s);
    }
}
