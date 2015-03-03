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
import com.daviancorp.android.ui.adapter.WeaponListGeneralAdapter;
import com.daviancorp.android.ui.general.DrawSharpness;

import java.util.ArrayList;

public class WeaponBowgunListFragment extends WeaponListFragment implements
		LoaderCallbacks<ArrayList<Weapon>> {

	public static WeaponBowgunListFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString(WeaponListFragment.ARG_TYPE, type);
		WeaponBowgunListFragment f = new WeaponBowgunListFragment();
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
        View v = inflater.inflate(R.layout.fragment_generic_list, null);
        return v;
	}
	
	@Override
	public void onLoadFinished(Loader<ArrayList<Weapon>> loader, ArrayList<Weapon> weapons) {
		// Create an adapter to point at this cursor
		WeaponBowgunListAdapter adapter = new WeaponBowgunListAdapter(
				getActivity(), weapons);
		setListAdapter(adapter);

	}

	private static class WeaponBowgunListAdapter extends WeaponListGeneralAdapter {

        private static class ViewHolder extends GeneralViewHolder {
            // Gunner
            TextView recoiltv;
            TextView steadytv;
            TextView reloadtv;
        }

        public WeaponBowgunListAdapter(Context context, ArrayList<Weapon> weapons) {
            super(context, weapons);
        }


		@Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.fragment_weapon_tree_item_bowgun,
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
                // Bowgun views
                //
                holder.reloadtv = (TextView) convertView.findViewById(R.id.reload_text);
                holder.recoiltv = (TextView) convertView.findViewById(R.id.recoil_text);
                holder.steadytv = (TextView) convertView.findViewById(R.id.deviation_text);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            super.getView(position, convertView, parent);

			// Get the monster for the current row
			Weapon weapon = getItem(position);

			String reload = weapon.getReloadSpeed();
			String recoil = weapon.getRecoil();
			String steady = weapon.getDeviation();

			
			if (steady.startsWith("Left/Right")) {
				String[] tempSteady = steady.split(":");
				steady = "L/R:" + tempSteady[1];
			}
            else if (steady.equals("None"))
            {
                steady = "";
            }

			holder.reloadtv.setText(reload);
			holder.recoiltv.setText(recoil);
			holder.steadytv.setText(steady);

            return convertView;
		}
	}

}
