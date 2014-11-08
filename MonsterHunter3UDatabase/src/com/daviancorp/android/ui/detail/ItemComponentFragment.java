package com.daviancorp.android.ui.detail;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Component;
import com.daviancorp.android.data.classes.Item;
import com.daviancorp.android.data.database.ComponentCursor;
import com.daviancorp.android.data.database.S;
import com.daviancorp.android.loader.ComponentListCursorLoader;
import com.daviancorp.android.mh3udatabase.R;

public class ItemComponentFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String ARG_ITEM_ID = "COMPONENT_ID";

	public static ItemComponentFragment newInstance(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, id);
		ItemComponentFragment f = new ItemComponentFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.item_component_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_component_list, null);
		return v;
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long mId = -1;
		if (args != null) {
			mId = args.getLong(ARG_ITEM_ID);
		}
		return new ComponentListCursorLoader(getActivity(), 
				ComponentListCursorLoader.FROM_COMPONENT, mId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		ComponentListCursorAdapter adapter = new ComponentListCursorAdapter(
				getActivity(), (ComponentCursor) cursor);
		setListAdapter(adapter);

	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Item ID; CursorAdapter gives us this
		// for free
		Intent i = null;
		long tagId = (long) v.getTag();
		
		if (tagId >= S.SECTION_WEAPON) {
			i = new Intent(getActivity(), WeaponDetailActivity.class);
			i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, tagId);
		}
		else if (tagId >= S.SECTION_ARMOR) {
			i = new Intent(getActivity(), ArmorDetailActivity.class);
			i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, tagId);
		}
		else if (tagId >= S.SECTION_DECORATIONS) {
			i = new Intent(getActivity(), DecorationDetailActivity.class);
			i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, tagId);
		}
		else {
			i = new Intent(getActivity(), ItemDetailActivity.class);
			i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, tagId);
		}
		startActivity(i);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	private static class ComponentListCursorAdapter extends CursorAdapter {

		private ComponentCursor mComponentCursor;

		public ComponentListCursorAdapter(Context context, ComponentCursor cursor) {
			super(context, cursor, 0);
			mComponentCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_component_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			Component component = mComponentCursor.getComponent();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);
			ImageView itemImageView = (ImageView) view.findViewById(R.id.item_image);
			TextView itemTextView = (TextView) view.findViewById(R.id.item);
			TextView amtTextView = (TextView) view.findViewById(R.id.amt);
			TextView typeTextView = (TextView) view.findViewById(R.id.type);
			
			Item created = component.getCreated();
			long createdId = created.getId();
			
			String nameText = created.getName();
			String amtText = "" + component.getQuantity();
			String typeText = "" + component.getType();
			
			itemTextView.setText(nameText);
			amtTextView.setText(amtText);
			typeTextView.setText(typeText);
			
			Drawable i = null;
			String cellImage = "";
			
			if (created.getType().equals("Decoration")) {
				cellImage = "icons_items/" + created.getFileLocation();
			} 
			else if ((createdId >= S.SECTION_HEAD) && (createdId < S.SECTION_BODY)) {
				cellImage = "icons_armor/icons_head/head" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_BODY) && (createdId < S.SECTION_ARMS)) {
				cellImage = "icons_armor/icons_body/body" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_ARMS) && (createdId < S.SECTION_WAIST)) {
				cellImage = "icons_armor/icons_arms/arms" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_WAIST) && (createdId < S.SECTION_LEGS)) {
				cellImage = "icons_armor/icons_waist/waist" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_LEGS) && (createdId < S.SECTION_GREAT_SWORD)) {
				cellImage = "icons_armor/icons_legs/legs" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_GREAT_SWORD) && (createdId < S.SECTION_HUNTING_HORN)) {
				cellImage = "icons_weapons/icons_great_sword/great_sword" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_HUNTING_HORN) && (createdId < S.SECTION_LONG_SWORD)) {
				cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_LONG_SWORD) && (createdId < S.SECTION_SWORD_AND_SHIELD)) {
				cellImage = "icons_weapons/icons_long_sword/long_sword" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_SWORD_AND_SHIELD) && (createdId < S.SECTION_DUAL_BLADES)) {
				cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_DUAL_BLADES) && (createdId < S.SECTION_HAMMER)) {
				cellImage = "icons_weapons/icons_dual_blades/dual_blades" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_HAMMER) && (createdId < S.SECTION_LANCE)) {
				cellImage = "icons_weapons/icons_hammer/hammer" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_LANCE) && (createdId < S.SECTION_GUNLANCE)) {
				cellImage = "icons_weapons/icons_lance/lance" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_GUNLANCE) && (createdId < S.SECTION_SWITCH_AXE)) {
				cellImage = "icons_weapons/icons_gunlance/gunlance" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_SWITCH_AXE) && (createdId < S.SECTION_LIGHT_BOWGUN)) {
				cellImage = "icons_weapons/icons_switch_axe/switch_axe" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_LIGHT_BOWGUN) && (createdId < S.SECTION_HEAVY_BOWGUN)) {
				cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + created.getRarity() + ".png";
			}
			else if ((createdId >= S.SECTION_HEAVY_BOWGUN) && (createdId < S.SECTION_BOW)) {
				cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + created.getRarity() + ".png";
			}
			else if (createdId >= S.SECTION_BOW) {
				cellImage = "icons_weapons/icons_bow/bow" + created.getRarity() + ".png";
			}
			
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			itemImageView.setImageDrawable(i);

			itemLayout.setTag(createdId);
		}
	}

}
