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

public class WeaponBowgunListFragment extends WeaponListFragment implements
		LoaderCallbacks<Cursor> {

	public static WeaponBowgunListFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString(WeaponListFragment.ARG_TYPE, type);
		WeaponBowgunListFragment f = new WeaponBowgunListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_generic_list, container,false);
		return v;
	}
	
	@Override
	protected CursorAdapter getDetailAdapter() {
		return (CursorAdapter) getListAdapter();
	}
	
	@Override
	protected Weapon getDetailWeapon(int position) {
		WeaponBowgunListCursorAdapter adapter = (WeaponBowgunListCursorAdapter) getListAdapter();
		return((WeaponCursor) adapter.getItem(position)).getWeapon();
	}
	
	@Override
	protected Fragment getThisFragment() {
		return this;
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		WeaponBowgunListCursorAdapter adapter = new WeaponBowgunListCursorAdapter(
				getActivity(), (WeaponCursor) cursor);
		setListAdapter(adapter);

	}

	private static class WeaponBowgunListCursorAdapter extends WeaponListGeneralAdapter {

        private static class ViewHolder extends GeneralViewHolder {
            // Gunner
            TextView recoiltv;
            TextView steadytv;
            TextView reloadtv;
        }

		public WeaponBowgunListCursorAdapter(Context context, WeaponCursor cursor) {
			super(context, cursor);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fragment_weapon_tree_item_bowgun, parent,
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

            // Bowgun stuff
            holder.reloadtv = (TextView) view.findViewById(R.id.reload_text);
            holder.recoiltv = (TextView) view.findViewById(R.id.recoil_text);
            holder.steadytv = (TextView) view.findViewById(R.id.deviation_text);

            view.setTag(holder);

            return view;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            ViewHolder holder = (ViewHolder) view.getTag();

			// Get the monster for the current row
			Weapon weapon = mWeaponCursor.getWeapon();

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

		}
	}

}
