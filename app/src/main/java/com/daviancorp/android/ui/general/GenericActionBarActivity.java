package com.daviancorp.android.ui.general;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.dialog.AboutDialogFragment;
import com.daviancorp.android.ui.list.ArmorListActivity;
import com.daviancorp.android.ui.list.CombiningListActivity;
import com.daviancorp.android.ui.list.DecorationListActivity;
import com.daviancorp.android.ui.list.ItemListActivity;
import com.daviancorp.android.ui.list.LocationListActivity;
import com.daviancorp.android.ui.list.MonsterListActivity;
import com.daviancorp.android.ui.list.QuestListActivity;
import com.daviancorp.android.ui.list.SkillTreeListActivity;
import com.daviancorp.android.ui.list.WeaponSelectionListActivity;
import com.daviancorp.android.ui.list.WishlistListActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

import java.io.IOException;

/*
 * Any subclass needs to:
 *  - override onCreate() to set title
 *  - override createFragment() for detail fragments
 */

public abstract class GenericActionBarActivity extends ActionBarActivity {

    protected static final String DIALOG_ABOUT = "about";

    protected Fragment detail;
    private ListView mDrawerList;
    private DrawerAdapter mDrawerAdapter;
    public ActionBarDrawerToggle mDrawerToggle;
    public DrawerLayout mDrawerLayout;
    private static boolean drawerOpened = false;

    public interface ActionOnCloseListener {
        void actionOnClose();

    }

    public ActionOnCloseListener actionOnCloseListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Set up drawer toggle actions
    public void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerLayout.setStatusBarBackgroundColor(#000000); // I think this is used to have the drawer behind the status bar
        // Populate navigation drawer
        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Set navigation actions
                Intent intent = new Intent();

                switch (position) {
                    case 0: // Monsters
                        intent = new Intent(getApplicationContext(), MonsterListActivity.class);
                        break;
                    case 1: // Weapons
                        intent = new Intent(getApplicationContext(), WeaponSelectionListActivity.class);
                        break;
                    case 2: // Armor
                        intent = new Intent(getApplicationContext(), ArmorListActivity.class);
                        break;
                    case 3: // Quests
                        intent = new Intent(getApplicationContext(), QuestListActivity.class);
                        break;
                    case 4: // Items
                        intent = new Intent(getApplicationContext(), ItemListActivity.class);
                        break;
                    case 5: // Combining
                        intent = new Intent(getApplicationContext(), CombiningListActivity.class);
                        break;
                    case 6: // Locations
                        intent = new Intent(getApplicationContext(), LocationListActivity.class);
                        break;
                    case 7: // Decorations
                        intent = new Intent(getApplicationContext(), DecorationListActivity.class);
                        break;
                    case 8: // Skill Trees
                        intent = new Intent(getApplicationContext(), SkillTreeListActivity.class);
                        break;
                    case 9: // Wishlists
                        intent = new Intent(getApplicationContext(), WishlistListActivity.class);
                        break;
                }
                final Intent finalIntent = intent;
                actionOnCloseListener = new ActionOnCloseListener() {
                    @Override
                    public void actionOnClose() {
                        startActivity(finalIntent);
                    }
                };

                mDrawerLayout.closeDrawers();
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // Creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // Creates call to onPrepareOptionsMenu()
                if (actionOnCloseListener != null) {
                    actionOnCloseListener.actionOnClose();
                    actionOnCloseListener = null;
                }
            }
        };

        // Enable menu button to toggle drawer
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //automatically open drawer on launch
        if(!drawerOpened)
        {
            mDrawerLayout.openDrawer(Gravity.LEFT);
            drawerOpened = true;
        }
    }

    // Set up drawer menu options
    private void addDrawerItems() {
        String[] menuArray = getResources().getStringArray(R.array.drawer_items);

        mDrawerAdapter = new DrawerAdapter(getApplicationContext(), R.layout.drawer_list_item, menuArray);
        mDrawerList.setAdapter(mDrawerAdapter);
    }


    public void enableDrawerIndicator() {
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    // Sync button animation sync with drawer state
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawerAdapter != null) {
            mDrawerAdapter.setSelectedIndex(getSelectedSection().menuListPosition);
        }
    }

    protected abstract MenuSection getSelectedSection();

    // Handle toggle state sync across configuration changes (rotation)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Detect navigation drawer item selected
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Detect home and or expansion menu item selections
        switch (item.getItemId()) {
            case R.id.about:
                FragmentManager fm = getSupportFragmentManager();
                AboutDialogFragment dialog = new AboutDialogFragment();
                dialog.show(fm, DIALOG_ABOUT);
                return true;

            case R.id.send_feedback:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("text/email");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"monster-hunter-database-feedback@googlegroups.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "MH4U Database Feedback");
                startActivity(Intent.createChooser(email, "Send Feedback:"));

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            String title = mi.getTitle().toString();
            Spannable newTitle = new SpannableString(title);
            newTitle.setSpan(new ForegroundColorSpan(Color.WHITE), 0,
                    newTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mi.setTitle(newTitle);
        }
        return true;
    }

    public void onBackPressed() {
        // If back is pressed while drawer is open, close drawer.
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    public Fragment getDetail() {
        return detail;
    }


    // Custom adapter needed to display list items with icons
    public class DrawerAdapter extends ArrayAdapter<String> {

        Context context;
        int layoutResourceId;
        String[] items;

        public void setSelectedIndex(int selectedIndex) {
            this.selectedIndex = selectedIndex;
        }

        int selectedIndex;


        public DrawerAdapter(Context context, int layoutResourceId, String[] items) {
            super(context, layoutResourceId, items);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ItemHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ItemHolder();
                holder.imgIcon = (ImageView) row.findViewById(R.id.nav_list_icon);
                holder.txtTitle = (TextView) row.findViewById(R.id.nav_list_item);

                row.setTag(holder);
            } else {
                holder = (ItemHolder) row.getTag();
            }

            String[] singleItem = items[position].split(",");
            holder.txtTitle.setText(singleItem[0]);
            holder.txtTitle.setTextColor(getResources().getColor(position == selectedIndex ? R.color.accent_color : R.color.list_text));

            // Attempt to retrieve drawable
            Drawable i = null;
            String cellImage = singleItem[1];
            try {
                i = Drawable.createFromStream(
                        context.getAssets().open(cellImage), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.imgIcon.setImageDrawable(i);

            return row;
        }


        class ItemHolder {
            ImageView imgIcon;
            TextView txtTitle;
        }
    }
}