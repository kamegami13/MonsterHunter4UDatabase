package com.daviancorp.android.ui.list;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.WeaponListElementAdapter;

public class WeaponBowListFragment extends WeaponListFragment implements
		LoaderCallbacks<Cursor> {

	public static WeaponBowListFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString(WeaponListFragment.ARG_TYPE, type);
		WeaponBowListFragment f = new WeaponBowListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        //		super.setContextMenu(v);
		return inflater.inflate(R.layout.fragment_generic_list, container,false);
	}
	
	@Override
	protected CursorAdapter getDetailAdapter() {
		return (CursorAdapter) getListAdapter();
	}
	
	@Override
	protected Weapon getDetailWeapon(int position) {
		WeaponBowListCursorAdapter adapter = (WeaponBowListCursorAdapter) getListAdapter();
		return((WeaponCursor) adapter.getItem(position)).getWeapon();
	}	
	
	@Override
	protected Fragment getThisFragment() {
		return this;
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		WeaponBowListCursorAdapter adapter = new WeaponBowListCursorAdapter(
				getActivity(), (WeaponCursor) cursor);
		setListAdapter(adapter);

	}

	private static class WeaponBowListCursorAdapter extends WeaponListElementAdapter {

		public WeaponBowListCursorAdapter(Context context, WeaponCursor cursor) {
			super(context, cursor);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_weapon_tree_item_bow,
					parent, false);
		}

		public void bindView(View view, Context context, Cursor cursor) {
            String a = "";

            super.bindView(view, context, cursor);

			// Get the weapon for the current row
			Weapon weapon = mWeaponCursor.getWeapon();

			// Bow stuff
			TextView arctv = (TextView) view.findViewById(R.id.arc_shot_text);
			TextView chargetv = (TextView) view.findViewById(R.id.charge_text);

			String arc = weapon.getRecoil();
			String charge = weapon.getCharges();
			String chargeText = "|";

			String[] charges = charge.split("\\|");
			for (String c : charges) {
				chargeText = chargeText + getChargeData(c);
			}

			arctv.setText(arc);
			chargetv.setText(chargeText);

			// Coatings
			ImageView powerv = (ImageView) view.findViewById(R.id.power);
			ImageView crangev = (ImageView) view.findViewById(R.id.crange);
			ImageView poisonv = (ImageView) view.findViewById(R.id.poison);
			ImageView parav = (ImageView) view.findViewById(R.id.para);
			ImageView sleepv = (ImageView) view.findViewById(R.id.sleep);
			ImageView exhaustv = (ImageView) view.findViewById(R.id.exhaust);
			ImageView slimev = (ImageView) view.findViewById(R.id.blast);
			ImageView paintv = (ImageView) view.findViewById(R.id.paint);
			
			// Clear images
			powerv.setImageDrawable(null);
			crangev.setImageDrawable(null);
			poisonv.setImageDrawable(null);
			parav.setImageDrawable(null);
			sleepv.setImageDrawable(null);
			exhaustv.setImageDrawable(null);
			slimev.setImageDrawable(null);
			paintv.setImageDrawable(null);

            powerv.setVisibility(View.GONE);
            crangev.setVisibility(View.GONE);
            poisonv.setVisibility(View.GONE);
            parav.setVisibility(View.GONE);
            sleepv.setVisibility(View.GONE);
            exhaustv.setVisibility(View.GONE);
            slimev.setVisibility(View.GONE);
            paintv.setVisibility(View.GONE);


			String[] coatings = weapon.getCoatings().split("\\|");

			if (!coatings[0].equals("-")) {
				powerv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Red.png"));
                powerv.setVisibility(View.VISIBLE);
			}
			if (!coatings[1].equals("-")) {
				poisonv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Purple.png"));
                poisonv.setVisibility(View.VISIBLE);
			}
			if (!coatings[2].equals("-")) {
				parav.setImageDrawable(getDrawable(context, "icons_items/Bottle-Yellow.png"));
                parav.setVisibility(View.VISIBLE);
			}
			if (!coatings[3].equals("-")) {
				sleepv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Cyan.png"));
                sleepv.setVisibility(View.VISIBLE);
			}
			if (!coatings[4].equals("-")) {
				crangev.setImageDrawable(getDrawable(context, "icons_items/Bottle-White.png"));
                crangev.setVisibility(View.VISIBLE);
			}
			if (!coatings[5].equals("-")) {
				paintv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Pink.png"));
                paintv.setVisibility(View.VISIBLE);
			}
			if (!coatings[6].equals("-")) {
				exhaustv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Blue.png"));
                exhaustv.setVisibility(View.VISIBLE);
			}
			if (!coatings[7].equals("-")) {
				slimev.setImageDrawable(getDrawable(context, "icons_items/Bottle-Orange.png"));
                slimev.setVisibility(View.VISIBLE);
			}

		}

		private String getChargeData(String charge) {
			String s = "";

			if (charge.startsWith("Scatter")) {
				s = "S";
			} else if (charge.startsWith("Rapid")) {
				s = "R";
			} else if (charge.startsWith("Pierce")) {
				s = "P";
			}

            if (charge.endsWith("*")) {
                s = s + charge.charAt(charge.length() - 2) + "|";
            }
            else {
                s = s + charge.charAt(charge.length() - 1) + "|";
            }
			return s;
		}
	}

}
