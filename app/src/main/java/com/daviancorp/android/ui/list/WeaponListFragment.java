package com.daviancorp.android.ui.list;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.ListView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.loader.WeaponListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.WeaponDetailActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class WeaponListFragment extends ListFragment implements
		LoaderCallbacks<ArrayList<Weapon>> {
	protected static final String ARG_TYPE = "WEAPON_TYPE";

//	private static final String DIALOG_WISHLIST_DATA_ADD_MULTI = "wishlist_data_add_multi";
//	private static final int REQUEST_ADD_MULTI = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public Loader<ArrayList<Weapon>> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		String mType = null;
		if (args != null) {
			mType = args.getString(ARG_TYPE);
		}
		return new WeaponArrayLoader(getActivity().getApplicationContext(), mType);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Weapon>> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	@Override
	public abstract void onLoadFinished(Loader<ArrayList<Weapon>> loader, ArrayList<Weapon> weapons);
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Weapon ID; CursorAdapter gives us this
		// for free
		Intent i = new Intent(getActivity(), WeaponDetailActivity.class);
		i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, id);
		startActivity(i);
	}

    static class WeaponArrayLoader extends AsyncTaskLoader<ArrayList<Weapon>> {
        String mType;
        public WeaponArrayLoader(Context context, String type) {
            super(context);
            mType = type;
        }

        public ArrayList<Weapon> loadInBackground() {
            return DataManager.get(getContext()).queryWeaponTypeArray(mType);
        }
    }

	
	//protected abstract CursorAdapter getDetailAdapter();
	
	//protected abstract Weapon getDetailWeapon(int position);
	
	//protected abstract Fragment getThisFragment();
}
