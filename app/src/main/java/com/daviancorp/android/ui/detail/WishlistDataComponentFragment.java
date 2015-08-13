package com.daviancorp.android.ui.detail;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.*;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.daviancorp.android.data.classes.WishlistComponent;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.WishlistComponentCursor;
import com.daviancorp.android.loader.WishlistComponentListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.dialog.WishlistComponentEditDialogFragment;

public class WishlistDataComponentFragment extends ListFragment implements
        LoaderCallbacks<Cursor> {

    public static final String EXTRA_COMPONENT_REFRESH =
            "com.daviancorp.android.ui.general.wishlist_component_refresh";

    private static final String ARG_ID = "ID";

    private static final String DIALOG_WISHLIST_COMPONENT_EDIT = "wishlist_component_edit";
    private static final int REQUEST_REFRESH = 0;
    private static final int REQUEST_EDIT = 1;

    private long wishlistId;
    private ListView mListView;
    private TextView mHeaderTextView, mItemTypeTextView, mQuantityTypeTextView, mExtraTypeTextView;
    private ActionMode mActionMode;

    private boolean started, fromOtherTab;

    public static WishlistDataComponentFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        WishlistDataComponentFragment f = new WishlistDataComponentFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.wishlist_data_component_fragment, getArguments(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wishlist_component_list, container, false);

        mListView = (ListView) v.findViewById(android.R.id.list);
        mHeaderTextView = (TextView) v.findViewById(R.id.header);
        mItemTypeTextView = (TextView) v.findViewById(R.id.item_type);
        mQuantityTypeTextView = (TextView) v.findViewById(R.id.quantity_type);
        mExtraTypeTextView = (TextView) v.findViewById(R.id.extra_type);

        mItemTypeTextView.setText("Item");
        mQuantityTypeTextView.setText("Required");
        mExtraTypeTextView.setText("Have");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // Use floating context menus on Froyo and Gingerbread
            registerForContextMenu(mListView);
        } else {
            // Use contextual action bar on Honeycomb and higher
            mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mActionMode != null) {
                        return false;
                    }

                    mActionMode = getActivity().startActionMode(new mActionModeCallback());
                    mActionMode.setTag(position);
                    mListView.setItemChecked(position, true);
                    return true;
                }
            });
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_wishlist_edit, menu);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            MenuItem item_down = menu.findItem(R.id.wishlist_edit);
            item_down.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wishlist_edit:
                if (mListView.getAdapter().getCount() > 0) {
                    mActionMode = getActivity().startActionMode(new mActionModeCallback());
                    mActionMode.setTag(0);
                    mListView.setItemChecked(0, true);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_wishlist_data_component, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_EDIT) {
            if (data.getBooleanExtra(WishlistComponentEditDialogFragment.EXTRA_EDIT, false)) {
                updateUI();
            }
        } else if (requestCode == REQUEST_REFRESH) {
            if (data.getBooleanExtra(WishlistDataDetailFragment.EXTRA_DETAIL_REFRESH, false)) {
                updateUI();
            }
        }
    }

    private void updateUI() {
        if (started) {
            getLoaderManager().getLoader(R.id.wishlist_data_component_fragment).forceLoad();
            WishlistComponentCursorAdapter adapter = (WishlistComponentCursorAdapter) getListAdapter();
            adapter.notifyDataSetChanged();

            if (!fromOtherTab) {
                sendResult(Activity.RESULT_OK, true);
            } else {
                fromOtherTab = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void sendResult(int resultCode, boolean refresh) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_COMPONENT_REFRESH, refresh);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        boolean temp = onItemSelected(item, position);

        if (temp) {
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private boolean onItemSelected(MenuItem item, int position) {
        WishlistComponentCursorAdapter adapter = (WishlistComponentCursorAdapter) getListAdapter();
        WishlistComponent wishlistComponent = ((WishlistComponentCursor) adapter.getItem(position)).getWishlistComponent();
        long id = wishlistComponent.getId();
        String name = wishlistComponent.getItem().getName();

        FragmentManager fm = getActivity().getSupportFragmentManager();

        switch (item.getItemId()) {
            case R.id.menu_item_edit_wishlist_data:
                WishlistComponentEditDialogFragment dialogEdit = WishlistComponentEditDialogFragment.newInstance(id, name);
                dialogEdit.setTargetFragment(WishlistDataComponentFragment.this, REQUEST_EDIT);
                dialogEdit.show(fm, DIALOG_WISHLIST_COMPONENT_EDIT);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // The id argument will be the Item ID; CursorAdapter gives us this
        // for free
        if (mActionMode == null) {
            mListView.setItemChecked(position, false);
            Intent i = null;
            long mId = (long) v.getTag();
            String itemtype;

            WishlistComponent component;

            WishlistComponentCursor mycursor = (WishlistComponentCursor) l.getItemAtPosition(position);
            component = mycursor.getWishlistComponent();
            itemtype = component.getItem().getType();

//			if (mId < S.SECTION_ARMOR) {
//				i = new Intent(getActivity(), ItemDetailActivity.class);
//				i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, mId);
//			}
//			else if (mId < S.SECTION_WEAPON) {
//				i = new Intent(getActivity(), ArmorDetailActivity.class);
//				i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, mId);
//			}
//			else {
//				i = new Intent(getActivity(), WeaponDetailActivity.class);
//				i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, mId);
//			}

            switch (itemtype) {
                case "Weapon":
                    i = new Intent(getActivity(), WeaponDetailActivity.class);
                    i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, mId);
                    break;
                case "Armor":
                    i = new Intent(getActivity(), ArmorDetailActivity.class);
                    i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, mId);
                    break;
                case "Decoration":
                    i = new Intent(getActivity(), DecorationDetailActivity.class);
                    i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, mId);
                    break;
                default:
                    i = new Intent(getActivity(), ItemDetailActivity.class);
                    i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, mId);
            }

            startActivity(i);
        }
        // Contextual action bar options
        else {
            mActionMode.setTag(position);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // You only ever load the runs, so assume this is the case
        wishlistId = -1;
        if (args != null) {
            wishlistId = args.getLong(ARG_ID);
        }
        return new WishlistComponentListCursorLoader(getActivity(), wishlistId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Create an adapter to point at this cursor
        WishlistComponentCursorAdapter adapter = new WishlistComponentCursorAdapter(
                getActivity(), (WishlistComponentCursor) cursor);
        setListAdapter(adapter);
        started = true;

        // Show the total price
        int totalPrice = DataManager.get(getActivity()).queryWishlistPrice(wishlistId);
        mHeaderTextView.setText("Total Cost:   " + totalPrice + "z");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Stop using the cursor (via the adapter)
        setListAdapter(null);
    }

    private static class WishlistComponentCursorAdapter extends CursorAdapter {

        private WishlistComponentCursor mWishlistComponentCursor;

        public WishlistComponentCursorAdapter(Context context, WishlistComponentCursor cursor) {
            super(context, cursor, 0);
            mWishlistComponentCursor = cursor;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Use a layout inflater to get a row view
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.fragment_wishlist_data_listitem,
                    parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Get the skill for the current row
            WishlistComponent component = mWishlistComponentCursor.getWishlistComponent();

            // Set up the text view
            LinearLayout root = (LinearLayout) view.findViewById(R.id.listitem);
            ImageView itemImageView = (ImageView) view.findViewById(R.id.item_image);
            TextView itemTextView = (TextView) view.findViewById(R.id.item);
            TextView amtTextView = (TextView) view.findViewById(R.id.amt);
            TextView extraTextView = (TextView) view.findViewById(R.id.extra);

            long id = component.getItem().getId();
            int quantity = component.getQuantity();
            int notes = component.getNotes();

            String nameText = component.getItem().getName();
            String amtText = "" + quantity;
            String extraText = "" + notes;

            itemTextView.setText(nameText);
            amtTextView.setText(amtText);
            extraTextView.setText(extraText);

            itemTextView.setTextColor(Color.BLACK);
            if (notes >= quantity) {
                itemTextView.setTextColor(Color.RED);
            }

            Drawable i = null;
            String cellImage = "";
            String cellRare = "" + component.getItem().getRarity();

            String sub_type = component.getItem().getSubType();

            switch (sub_type) {
                case "Head":
                    cellImage = "icons_armor/icons_head/head" + cellRare + ".png";
                    break;
                case "Body":
                    cellImage = "icons_armor/icons_body/body" + cellRare + ".png";
                    break;
                case "Arms":
                    cellImage = "icons_armor/icons_body/body" + cellRare + ".png";
                    break;
                case "Waist":
                    cellImage = "icons_armor/icons_waist/waist" + cellRare + ".png";
                    break;
                case "Legs":
                    cellImage = "icons_armor/icons_legs/legs" + cellRare + ".png";
                    break;
                case "Great Sword":
                    cellImage = "icons_weapons/icons_great_sword/great_sword" + cellRare + ".png";
                    break;
                case "Long Sword":
                    cellImage = "icons_weapons/icons_long_sword/long_sword" + cellRare + ".png";
                    break;
                case "Sword and Shield":
                    cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + cellRare + ".png";
                    break;
                case "Dual Blades":
                    cellImage = "icons_weapons/icons_dual_blades/dual_blades" + cellRare + ".png";
                    break;
                case "Hammer":
                    cellImage = "icons_weapons/icons_hammer/hammer" + cellRare + ".png";
                    break;
                case "Hunting Horn":
                    cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + cellRare + ".png";
                    break;
                case "Lance":
                    cellImage = "icons_weapons/icons_hammer/hammer" + cellRare + ".png";
                    break;
                case "Gunlance":
                    cellImage = "icons_weapons/icons_gunlance/gunlance" + cellRare + ".png";
                    break;
                case "Switch Axe":
                    cellImage = "icons_weapons/icons_switch_axe/switch_axe" + cellRare + ".png";
                    break;
                case "Charge Blade":
                    cellImage = "icons_weapons/icons_charge_blade/charge_blade" + cellRare + ".png";
                    break;
                case "Insect Glaive":
                    cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + cellRare + ".png";
                    break;
                case "Light Bowgun":
                    cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + cellRare + ".png";
                    break;
                case "Heavy Bowgun":
                    cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + cellRare + ".png";
                    break;
                case "Bow":
                    cellImage = "icons_weapons/icons_bow/bow" + cellRare + ".png";
                    break;
                default:
                    cellImage = "icons_items/" + component.getItem().getFileLocation();
            }

            try {
                i = Drawable.createFromStream(
                        context.getAssets().open(cellImage), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            itemImageView.setImageDrawable(i);

            root.setTag(id);
        }
    }

    private class mActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_wishlist_data_component, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int position = Integer.parseInt(mode.getTag().toString());
            mode.finish();
            return onItemSelected(item, position);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            for (int i = 0; i < mListView.getCount(); i++) {
                mListView.setItemChecked(i, false);
            }

            mActionMode = null;
        }
    }
}
