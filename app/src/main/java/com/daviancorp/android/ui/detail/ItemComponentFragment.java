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
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.S;
import com.daviancorp.android.loader.ArmorLoader;
import com.daviancorp.android.loader.ComponentListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.ArmorClickListener;
import com.daviancorp.android.ui.ClickListeners.DecorationClickListener;
import com.daviancorp.android.ui.ClickListeners.ItemClickListener;
import com.daviancorp.android.ui.ClickListeners.WeaponClickListener;

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
		View v = inflater.inflate(R.layout.fragment_generic_list, null);
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
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	protected static class ComponentListCursorAdapter extends CursorAdapter {

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

            String sub_type = created.getSubType();

            switch(sub_type){
                case "Head":
                    cellImage = "icons_armor/icons_head/head" + created.getRarity() + ".png";
                    break;
                case "Body":
                    cellImage = "icons_armor/icons_body/body" + created.getRarity() + ".png";
                    break;
                case "Arms":
                    cellImage = "icons_armor/icons_arms/arms" + created.getRarity() + ".png";
                    break;
                case "Waist":
                    cellImage = "icons_armor/icons_waist/waist" + created.getRarity() + ".png";
                    break;
                case "Legs":
                    cellImage = "icons_armor/icons_legs/legs" + created.getRarity() + ".png";
                    break;
                case "Great Sword":
                    cellImage = "icons_weapons/icons_great_sword/great_sword" + created.getRarity() + ".png";
                    break;
                case "Long Sword":
                    cellImage = "icons_weapons/icons_long_sword/long_sword" + created.getRarity() + ".png";
                    break;
                case "Sword and Shield":
                    cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + created.getRarity() + ".png";
                    break;
                case "Dual Blades":
                    cellImage = "icons_weapons/icons_dual_blades/dual_blades" + created.getRarity() + ".png";
                    break;
                case "Hammer":
                    cellImage = "icons_weapons/icons_hammer/hammer" + created.getRarity() + ".png";
                    break;
                case "Hunting Horn":
                    cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + created.getRarity() + ".png";
                    break;
                case "Lance":
                    cellImage = "icons_weapons/icons_lance/lance" + created.getRarity() + ".png";
                    break;
                case "Gunlance":
                    cellImage = "icons_weapons/icons_gunlance/gunlance" + created.getRarity() + ".png";
                    break;
                case "Switch Axe":
                    cellImage = "icons_weapons/icons_switch_axe/switch_axe" + created.getRarity() + ".png";
                    break;
                case "Charge Blade":
                    cellImage = "icons_weapons/icons_charge_blade/charge_blade" + created.getRarity() + ".png";
                    break;
                case "Insect Glaive":
                    cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + created.getRarity() + ".png";
                    break;
                case "Light Bowgun":
                    cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + created.getRarity() + ".png";
                    break;
                case "Heavy Bowgun":
                    cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + created.getRarity() + ".png";
                    break;
                case "Bow":
                    cellImage = "icons_weapons/icons_bow/bow" + created.getRarity() + ".png";
                    break;
                default:
                    cellImage = "icons_items/" + created.getFileLocation();
            }

			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			itemImageView.setImageDrawable(i);

			itemLayout.setTag(createdId);

            String itemtype = created.getType();

            switch(itemtype){
                case "Weapon":
                    itemLayout.setOnClickListener(new WeaponClickListener(context, createdId));
                    break;
                case "Armor":
                    itemLayout.setOnClickListener(new ArmorClickListener(context, createdId));
                    break;
                case "Decoration":
                    itemLayout.setOnClickListener(new DecorationClickListener(context, createdId));
                    break;
                default:
                    itemLayout.setOnClickListener(new ItemClickListener(context, createdId));
                    break;
            }

		}
	}

}
