package com.daviancorp.android.ui.detail;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.loader.WeaponTreeListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.WeaponClickListener;

public class WeaponTreeFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_WEAPON_ID = "WEAPON_ID";
	private long mWeaponId;
	
	public static WeaponTreeFragment newInstance(long weaponId) {
		Bundle args = new Bundle();
		args.putLong(ARG_WEAPON_ID, weaponId);
		WeaponTreeFragment f = new WeaponTreeFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.weapon_tree_fragment, getArguments(), this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		mWeaponId = args.getLong(ARG_WEAPON_ID, -1);
		
		return new WeaponTreeListCursorLoader(getActivity(), mWeaponId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		WeaponTreeListCursorAdapter adapter = new WeaponTreeListCursorAdapter(
				getActivity(), (WeaponCursor) cursor, mWeaponId);
		setListAdapter(adapter);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	private static class WeaponTreeListCursorAdapter extends CursorAdapter {

		private WeaponCursor mWeaponCursor;
		private long weaponId;

		public WeaponTreeListCursorAdapter(Context context, WeaponCursor cursor, long id) {
			super(context, cursor, 0);
			mWeaponCursor = cursor;
			weaponId = id;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_weapon_tree,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the weapon for the current row
			Weapon weapon = mWeaponCursor.getWeapon();

			// Set up the text view
			RelativeLayout weaponLayout = (RelativeLayout) view.findViewById(R.id.listitem);
			weaponLayout.setTag(weapon.getId());
            weaponLayout.setOnClickListener(new WeaponClickListener(context, weapon.getId()));
			
			TextView weaponView = (TextView) view.findViewById(R.id.name_text);
			String cellWeaponText = weapon.getName();

			if(weapon.getCreationCost() > 0){
				cellWeaponText = cellWeaponText + "\u2605";
			}

			weaponView.setText(cellWeaponText);

            View arrowView = (View) view.findViewById(R.id.arrow);
            arrowView.setVisibility(view.GONE);
			if ((weapon.getId() <= weaponId) && (weapon.getWFinal() == 0)) {
				arrowView.setVisibility(view.VISIBLE);
			}
			if (weapon.getId() == weaponId) {
				weaponView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			}
		}
	}

}
