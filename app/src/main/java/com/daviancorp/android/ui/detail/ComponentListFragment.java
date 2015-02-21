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
import com.daviancorp.android.data.database.ComponentCursor;
import com.daviancorp.android.loader.ComponentListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;

public class ComponentListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String ARG_ITEM_ID = "COMPONENT_ID";

	public static ComponentListFragment newInstance(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, id);
		ComponentListFragment f = new ComponentListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.component_list_fragment, getArguments(), this);
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
				ComponentListCursorLoader.FROM_CREATED, mId);
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
        int clickedId = (int) id;
        String itemtype;

        ComponentCursor mycursor = (ComponentCursor) l.getItemAtPosition(position);
        itemtype = mycursor.getItemType();

        switch(itemtype){
            case "Weapon":
                i = new Intent(getActivity(), WeaponDetailActivity.class);
                i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, tagId);
                break;
            case "Armor":
                i = new Intent(getActivity(), ArmorDetailActivity.class);
                i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, tagId);
                break;
            case "Decoration":
                i = new Intent(getActivity(), DecorationDetailActivity.class);
                i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, tagId);
                break;
            default:
                i = new Intent(getActivity(), ItemDetailActivity.class);
                i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, tagId);
        }
        if(i!=null)
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
			
			String nameText = component.getComponent().getName();
			String amtText = "" + component.getQuantity();
			String typeText = "" + component.getType();
			
			itemTextView.setText(nameText);
			amtTextView.setText(amtText);
			typeTextView.setText(typeText);
			
			Drawable i = null;
            String cellImage;
            
            String sub_type = component.getComponent().getSubType();

            switch(sub_type){
                case "Head":
                    cellImage = "icons_armor/icons_head/head" + component.getComponent().getRarity() + ".png";
                    break;
                case "Body":
                    cellImage = "icons_armor/icons_body/body" + component.getComponent().getRarity() + ".png";
                    break;
                case "Arms":
                    cellImage = "icons_armor/icons_body/body" + component.getComponent().getRarity() + ".png";
                    break;
                case "Waist":
                    cellImage = "icons_armor/icons_waist/waist" + component.getComponent().getRarity() + ".png";
                    break;
                case "Legs":
                    cellImage = "icons_armor/icons_legs/legs" + component.getComponent().getRarity() + ".png";
                    break;
                case "Great Sword":
                    cellImage = "icons_weapons/icons_great_sword/great_sword" + component.getComponent().getRarity() + ".png";
                    break;
                case "Long Sword":
                    cellImage = "icons_weapons/icons_long_sword/long_sword" + component.getComponent().getRarity() + ".png";
                    break;
                case "Sword and Shield":
                    cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + component.getComponent().getRarity() + ".png";
                    break;
                case "Dual Blades":
                    cellImage = "icons_weapons/icons_dual_blades/dual_blades" + component.getComponent().getRarity() + ".png";
                    break;
                case "Hammer":
                    cellImage = "icons_weapons/icons_hammer/hammer" + component.getComponent().getRarity() + ".png";
                    break;
                case "Hunting Horn":
                    cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + component.getComponent().getRarity() + ".png";
                    break;
                case "Lance":
                    cellImage = "icons_weapons/icons_hammer/hammer" + component.getComponent().getRarity() + ".png";
                    break;
                case "Gunlance":
                    cellImage = "icons_weapons/icons_gunlance/gunlance" + component.getComponent().getRarity() + ".png";
                    break;
                case "Switch Axe":
                    cellImage = "icons_weapons/icons_switch_axe/switch_axe" + component.getComponent().getRarity() + ".png";
                    break;
                case "Charge Blade":
                    cellImage = "icons_weapons/icons_charge_blade/charge_blade" + component.getComponent().getRarity() + ".png";
                    break;
                case "Insect Glaive":
                    cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + component.getComponent().getRarity() + ".png";
                    break;
                case "Light Bowgun":
                    cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + component.getComponent().getRarity() + ".png";
                    break;
                case "Heavy Bowgun":
                    cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + component.getComponent().getRarity() + ".png";
                    break;
                case "Bow":
                    cellImage = "icons_weapons/icons_bow/bow" + component.getComponent().getRarity() + ".png";
                    break;
                default:
                    cellImage = "icons_items/" + component.getComponent().getFileLocation();
            }
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			itemImageView.setImageDrawable(i);

			itemLayout.setTag(component.getComponent().getId());
		}
	}

}
