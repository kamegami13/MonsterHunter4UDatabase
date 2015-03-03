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

import java.util.ArrayList;

public class WeaponBowListFragment extends WeaponListFragment implements
		LoaderCallbacks<ArrayList<Weapon>> {

	public static WeaponBowListFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString(WeaponListFragment.ARG_TYPE, type);
		WeaponBowListFragment f = new WeaponBowListFragment();
		f.setArguments(args);
		return f;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.weapon_list_fragment, getArguments(), this).forceLoad();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        //		super.setContextMenu(v);
		return inflater.inflate(R.layout.fragment_generic_list, null);
	}
	
	/*@Override
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
	}*/
	
	@Override
	public void onLoadFinished(Loader<ArrayList<Weapon>> loader, ArrayList<Weapon> weapons) {
		// Create an adapter to point at this cursor
		WeaponBowListAdapter adapter = new WeaponBowListAdapter(
				getActivity(), weapons);
		setListAdapter(adapter);

	}

	private static class WeaponBowListAdapter extends WeaponListElementAdapter {

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

        public WeaponBowListAdapter(Context context, ArrayList<Weapon> weapons) {
            super(context, weapons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.fragment_weapon_tree_item_bow,
                        parent, false);

                //
                // GENERAL VIEWS
                //

                // Set the layout id
                holder.weaponLayout = (RelativeLayout) convertView.findViewById(R.id.main_layout);

                // Find all views
                holder.nametv = (TextView) convertView.findViewById(R.id.name_text);
                holder.attacktv = (TextView) convertView.findViewById(R.id.attack_text);
                holder.slottv = (TextView) convertView.findViewById(R.id.slots_text);
                holder.affinitytv = (TextView) convertView.findViewById(R.id.affinity_text);
                holder.defensetv = (TextView) convertView.findViewById(R.id.defense_text);
                holder.weaponIcon = (ImageView) convertView.findViewById(R.id.weapon_icon);
                holder.lineLayout = (View) convertView.findViewById(R.id.tree_lines);

                //
                // ELEMENT VIEWS
                //

                holder.elementtv = (TextView) convertView.findViewById(R.id.element_text);
                holder.elementtv2 = (TextView) convertView.findViewById(R.id.element_text2);
                holder.awakentv = (TextView) convertView.findViewById(R.id.awaken_text);
                holder.elementIcon = (ImageView) convertView.findViewById(R.id.element_image);
                holder.element2Icon = (ImageView) convertView.findViewById(R.id.element_image2);


                //
                // BOW VIEWS
                holder.arctv = (TextView) convertView.findViewById(R.id.arc_shot_text);
                holder.chargetv = (TextView) convertView.findViewById(R.id.charge_text);

                // Coatings
                holder.powerv = (ImageView) convertView.findViewById(R.id.power);
                holder.crangev = (ImageView) convertView.findViewById(R.id.crange);
                holder.poisonv = (ImageView) convertView.findViewById(R.id.poison);
                holder.parav = (ImageView) convertView.findViewById(R.id.para);
                holder.sleepv = (ImageView) convertView.findViewById(R.id.sleep);
                holder.exhaustv = (ImageView) convertView.findViewById(R.id.exhaust);
                holder.slimev = (ImageView) convertView.findViewById(R.id.blast);
                holder.paintv = (ImageView) convertView.findViewById(R.id.paint);


                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            super.getView(position, convertView, parent);

            // Get the weapon for the current row
            Weapon weapon = getItem(position);

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
                holder.powerv.setTag(weapon.getId());
                new LoadImage(holder.powerv, "icons_items/Bottle-Red.png").execute();
                holder.powerv.setVisibility(View.VISIBLE);
            }
            if (!coatings[1].equals("-")) {
                holder.poisonv.setTag(weapon.getId());
                new LoadImage(holder.poisonv, "icons_items/Bottle-Purple.png").execute();
                holder.poisonv.setVisibility(View.VISIBLE);
            }
            if (!coatings[2].equals("-")) {
                holder.parav.setTag(weapon.getId());
                new LoadImage(holder.parav, "icons_items/Bottle-Yellow.png").execute();
                holder.parav.setVisibility(View.VISIBLE);
            }
            if (!coatings[3].equals("-")) {
                holder.sleepv.setTag(weapon.getId());
                new LoadImage(holder.sleepv, "icons_items/Bottle-Cyan.png").execute();
                holder.sleepv.setVisibility(View.VISIBLE);
            }
            if (!coatings[4].equals("-")) {
                holder.crangev.setTag(weapon.getId());
                new LoadImage(holder.crangev, "icons_items/Bottle-White.png").execute();
                holder.crangev.setVisibility(View.VISIBLE);
            }
            if (!coatings[5].equals("-")) {
                holder.paintv.setTag(weapon.getId());
                new LoadImage(holder.paintv, "icons_items/Bottle-Pink.png").execute();
                holder.paintv.setVisibility(View.VISIBLE);
            }
            if (!coatings[6].equals("-")) {
                holder.exhaustv.setTag(weapon.getId());
                new LoadImage(holder.exhaustv, "icons_items/Bottle-Blue.png").execute();
                holder.exhaustv.setVisibility(View.VISIBLE);
            }
            if (!coatings[7].equals("-")) {
                holder.slimev.setTag(weapon.getId());
                new LoadImage(holder.slimev, "icons_items/Bottle-Orange.png").execute();
                holder.slimev.setVisibility(View.VISIBLE);
            }

            return convertView;
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
