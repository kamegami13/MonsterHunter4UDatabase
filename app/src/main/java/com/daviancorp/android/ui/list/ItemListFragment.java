package com.daviancorp.android.ui.list;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Item;
import com.daviancorp.android.data.database.ItemCursor;
import com.daviancorp.android.loader.ItemListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.ArmorClickListener;
import com.daviancorp.android.ui.ClickListeners.DecorationClickListener;
import com.daviancorp.android.ui.ClickListeners.ItemClickListener;
import com.daviancorp.android.ui.ClickListeners.WeaponClickListener;
import com.daviancorp.android.ui.detail.ArmorDetailActivity;
import com.daviancorp.android.ui.detail.DecorationDetailActivity;
import com.daviancorp.android.ui.detail.ItemDetailActivity;
import com.daviancorp.android.ui.detail.WeaponDetailActivity;

public class ItemListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private ItemListCursorAdapter mAdapter;
	private String mFilter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.item_list_fragment, null, this);
		
		mFilter = "";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_generic_list_search, null);
		
		EditText inputSearch = (EditText) v.findViewById(R.id.input_search);
		inputSearch.addTextChangedListener(new TextWatcher() {
		     
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // When user changed the Text
		    	mFilter = cs.toString();
		    	getLoaderManager().restartLoader(0, null, ItemListFragment.this);
		    }
		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {
		        // TODO Auto-generated method stub
		         
		    }
		     
		    @Override
		    public void afterTextChanged(Editable arg0) {
		        // TODO Auto-generated method stub                          
		    }
		});
		
		return v;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		return new ItemListCursorLoader(getActivity(), mFilter);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		mAdapter = new ItemListCursorAdapter(getActivity(), (ItemCursor) cursor);
		setListAdapter(mAdapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		// The id argument will be the Monster ID; CursorAdapter gives us this
//		// for free
//        Intent i;
//        String itemtype;
//
//        ItemCursor mycursor = (ItemCursor) l.getItemAtPosition(position);
//        itemtype = mycursor.getItem().getType();
//        long item_id = mycursor.getItem().getId();
//
//        switch(itemtype){
//            case "Weapon":
//                i = new Intent(getActivity(), WeaponDetailActivity.class);
//                i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, item_id);
//                break;
//            case "Armor":
//                i = new Intent(getActivity(), ArmorDetailActivity.class);
//                i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, item_id);
//                break;
//            case "Decoration":
//                i = new Intent(getActivity(), DecorationDetailActivity.class);
//                i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, item_id);
//                break;
//            default:
//                i = new Intent(getActivity(), ItemDetailActivity.class);
//                i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, item_id);
//        }
//		startActivity(i);
//	}

	private static class ItemListCursorAdapter extends CursorAdapter {

		private ItemCursor mItemCursor;

		public ItemListCursorAdapter(Context context, ItemCursor cursor) {
			super(context, cursor, 0);
			mItemCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			return inflater.inflate(R.layout.fragment_item_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			Item item = mItemCursor.getItem();

			// Set up the text view
            LinearLayout clickView = (LinearLayout) view.findViewById(R.id.listitem);

			TextView itemNameTextView = (TextView) view
					.findViewById(R.id.text1);
			ImageView itemImageView = (ImageView) view
					.findViewById(R.id.icon);

			String cellText = item.getName();
            String sub_type = item.getSubType();

            String cellImage;
            switch(sub_type){
                case "Head":
                    cellImage = "icons_armor/icons_head/head" + item.getRarity() + ".png";
                    break;
                case "Body":
                    cellImage = "icons_armor/icons_body/body" + item.getRarity() + ".png";
                    break;
                case "Arms":
                    cellImage = "icons_armor/icons_body/arms" + item.getRarity() + ".png";
                    break;
                case "Waist":
                    cellImage = "icons_armor/icons_waist/waist" + item.getRarity() + ".png";
                    break;
                case "Legs":
                    cellImage = "icons_armor/icons_legs/legs" + item.getRarity() + ".png";
                    break;
                case "Great Sword":
                    cellImage = "icons_weapons/icons_great_sword/great_sword" + item.getRarity() + ".png";
                    break;
                case "Long Sword":
                    cellImage = "icons_weapons/icons_long_sword/long_sword" + item.getRarity() + ".png";
                    break;
                case "Sword and Shield":
                    cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + item.getRarity() + ".png";
                    break;
                case "Dual Blades":
                    cellImage = "icons_weapons/icons_dual_blades/dual_blades" + item.getRarity() + ".png";
                    break;
                case "Hammer":
                    cellImage = "icons_weapons/icons_hammer/hammer" + item.getRarity() + ".png";
                    break;
                case "Hunting Horn":
                    cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + item.getRarity() + ".png";
                    break;
                case "Lance":
                    cellImage = "icons_weapons/icons_hammer/lance" + item.getRarity() + ".png";
                    break;
                case "Gunlance":
                    cellImage = "icons_weapons/icons_gunlance/gunlance" + item.getRarity() + ".png";
                    break;
                case "Switch Axe":
                    cellImage = "icons_weapons/icons_switch_axe/switch_axe" + item.getRarity() + ".png";
                    break;
                case "Charge Blade":
                    cellImage = "icons_weapons/icons_charge_blade/charge_blade" + item.getRarity() + ".png";
                    break;
                case "Insect Glaive":
                    cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + item.getRarity() + ".png";
                    break;
                case "Light Bowgun":
                    cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + item.getRarity() + ".png";
                    break;
                case "Heavy Bowgun":
                    cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + item.getRarity() + ".png";
                    break;
                case "Bow":
                    cellImage = "icons_weapons/icons_bow/bow" + item.getRarity() + ".png";
                    break;
                default:
                    cellImage = "icons_items/" + item.getFileLocation();
            }

			itemNameTextView.setText(cellText);

			Drawable itemImage = null;

			try {
				itemImage = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			itemImageView.setImageDrawable(itemImage);
            String itemtype = item.getType();
            long id = item.getId();

            switch(itemtype){
                case "Weapon":
                    clickView.setOnClickListener(new WeaponClickListener(context, id));
                    break;
                case "Armor":
                    clickView.setOnClickListener(new ArmorClickListener(context, id));
                    break;
                case "Decoration":
                    clickView.setOnClickListener(new DecorationClickListener(context, id));
                    break;
                default:
                    clickView.setOnClickListener(new ItemClickListener(context, id));
                    break;
            }

		}
	}

}
