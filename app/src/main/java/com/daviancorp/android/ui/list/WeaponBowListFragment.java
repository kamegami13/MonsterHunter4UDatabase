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
import android.widget.RelativeLayout;
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

        private static class ViewHolder extends ElementViewHolder {
            // Bow

            ImageView powerv;
            ImageView crangev;
            ImageView poisonv;
            ImageView parav;
            ImageView sleepv;
            ImageView exhaustv;
            ImageView slimev;
            ImageView paintv;

            TextView arctv;
            TextView chargetv;
        }

		public WeaponBowListCursorAdapter(Context context, WeaponCursor cursor) {
			super(context, cursor);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fragment_weapon_tree_item_bow, parent,
                    false);

            ViewHolder holder = new ViewHolder();

            //
            // GENERAL VIEWS
            //

            // Set the layout id
            holder.weaponLayout = (RelativeLayout) view.findViewById(R.id.main_layout);

            // Find all views
            holder.nametv = (TextView) view.findViewById(R.id.name_text);
            holder.attacktv = (TextView) view.findViewById(R.id.attack_text);
            holder.slottv = (TextView) view.findViewById(R.id.slots_text);
            holder.affinitytv = (TextView) view.findViewById(R.id.affinity_text);
            holder.defensetv = (TextView) view.findViewById(R.id.defense_text);
            holder.weaponIcon = (ImageView) view.findViewById(R.id.weapon_icon);
            holder.lineLayout = (View) view.findViewById(R.id.tree_lines);

            //
            // ELEMENT VIEWS
            //

            holder.elementtv = (TextView) view.findViewById(R.id.element_text);
            holder.elementtv2 = (TextView) view.findViewById(R.id.element_text2);
            holder.awakentv = (TextView) view.findViewById(R.id.awaken_text);
            holder.elementIcon = (ImageView) view.findViewById(R.id.element_image);
            holder.element2Icon = (ImageView) view.findViewById(R.id.element_image2);

            //
            // BOW VIEWS
            holder.arctv = (TextView) view.findViewById(R.id.arc_shot_text);
            holder.chargetv = (TextView) view.findViewById(R.id.charge_text);

            // Coatings
            holder.powerv = (ImageView) view.findViewById(R.id.power);
            holder.crangev = (ImageView) view.findViewById(R.id.crange);
            holder.poisonv = (ImageView) view.findViewById(R.id.poison);
            holder.parav = (ImageView) view.findViewById(R.id.para);
            holder.sleepv = (ImageView) view.findViewById(R.id.sleep);
            holder.exhaustv = (ImageView) view.findViewById(R.id.exhaust);
            holder.slimev = (ImageView) view.findViewById(R.id.blast);
            holder.paintv = (ImageView) view.findViewById(R.id.paint);



            view.setTag(holder);

            return view;
		}

		public void bindView(View view, Context context, Cursor cursor) {
            String a = "";

            super.bindView(view, context, cursor);

            ViewHolder holder = (ViewHolder) view.getTag();

			// Get the weapon for the current row
			Weapon weapon = mWeaponCursor.getWeapon();

			String arc = weapon.getRecoil();
			String charge = weapon.getCharges();
			String chargeText = "|";

			String[] charges = charge.split("\\|");
			for (String c : charges) {
				chargeText = chargeText + getChargeData(c);
			}

			holder.arctv.setText(arc);
			holder.chargetv.setText(chargeText);
			
			// Clear images
			holder.powerv.setImageDrawable(null);
            holder.crangev.setImageDrawable(null);
            holder.poisonv.setImageDrawable(null);
            holder.parav.setImageDrawable(null);
            holder.sleepv.setImageDrawable(null);
            holder.exhaustv.setImageDrawable(null);
            holder.slimev.setImageDrawable(null);
            holder.paintv.setImageDrawable(null);

            holder.powerv.setVisibility(View.GONE);
            holder.crangev.setVisibility(View.GONE);
            holder.poisonv.setVisibility(View.GONE);
            holder.parav.setVisibility(View.GONE);
            holder.sleepv.setVisibility(View.GONE);
            holder.exhaustv.setVisibility(View.GONE);
            holder.slimev.setVisibility(View.GONE);
            holder.paintv.setVisibility(View.GONE);


			String[] coatings = weapon.getCoatings().split("\\|");


			if (!coatings[0].equals("-")) {
                holder.powerv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Red.png"));
                holder.powerv.setVisibility(View.VISIBLE);
			}
			if (!coatings[1].equals("-")) {
                holder.poisonv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Purple.png"));
                holder.poisonv.setVisibility(View.VISIBLE);
			}
			if (!coatings[2].equals("-")) {
                holder.parav.setImageDrawable(getDrawable(context, "icons_items/Bottle-Yellow.png"));
                holder.parav.setVisibility(View.VISIBLE);
			}
			if (!coatings[3].equals("-")) {
                holder.sleepv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Cyan.png"));
                holder.sleepv.setVisibility(View.VISIBLE);
			}
			if (!coatings[4].equals("-")) {
                holder.crangev.setImageDrawable(getDrawable(context, "icons_items/Bottle-White.png"));
                holder.crangev.setVisibility(View.VISIBLE);
			}
			if (!coatings[5].equals("-")) {
                holder.paintv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Pink.png"));
                holder.paintv.setVisibility(View.VISIBLE);
			}
			if (!coatings[6].equals("-")) {
                holder.exhaustv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Blue.png"));
                holder.exhaustv.setVisibility(View.VISIBLE);
			}
			if (!coatings[7].equals("-")) {
                holder.slimev.setImageDrawable(getDrawable(context, "icons_items/Bottle-Orange.png"));
                holder.slimev.setVisibility(View.VISIBLE);
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
