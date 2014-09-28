package com.daviancorp.android.ui.list;

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

import com.daviancorp.android.data.object.Weapon;
import com.daviancorp.android.loader.WeaponListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.WeaponDetailActivity;

public abstract class WeaponListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	protected static final String ARG_TYPE = "WEAPON_TYPE";

//	private static final String DIALOG_WISHLIST_DATA_ADD_MULTI = "wishlist_data_add_multi";
//	private static final int REQUEST_ADD_MULTI = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.weapon_list_fragment, getArguments(), this);
	}

	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		String mType = null;
		if (args != null) {
			mType = args.getString(ARG_TYPE);
		}

		return new WeaponListCursorLoader(getActivity(), mType);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	@Override
	public abstract void onLoadFinished(Loader<Cursor> loader, Cursor cursor);
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Monster ID; CursorAdapter gives us this
		// for free
		Intent i = new Intent(getActivity(), WeaponDetailActivity.class);
		i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, id);
		startActivity(i);
	}
	
//	protected void setContextMenu(View v) {
//		ListView mListView = (ListView) v.findViewById(android.R.id.list);
//		
//		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//			// Use floating context menus on Froyo and Gingerbread
//			registerForContextMenu(mListView);
//		} else {
//			// Use contextual action bar on Honeycomb and higher
//			mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//			mListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
//				
//				public void onItemCheckedStateChanged(ActionMode mode, int position,
//						long id, boolean checked) {
//					// Required, but not used in this implementation
//				}
//				
//				// ActionMode.Callback methods
//				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//					MenuInflater inflater = mode.getMenuInflater();
//					inflater.inflate(R.menu.context_wishlist_data_add, menu);
//					return true;
//				}
//
//			    @Override
//			    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//			        return false;
//			     // Required, but not used in this implementation
//			    }
//
//			    @Override
//			    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {					
//			    	switch (item.getItemId()) {
//			    		case R.id.menu_item_wishlist_add:
//			    			CursorAdapter adapter = getDetailAdapter();
//			    			ArrayList<Long> idArray = new ArrayList<Long>();
//			    			
//			    			for (int i = 0; i < adapter.getCount(); i++) {
//			    				if (getListView().isItemChecked(i)) {
//			    					idArray.add(((WeaponCursor) adapter.getItem(i)).getWeapon().getId());
//			    				}
//			    			}
//			    			
//			    			long[] ids = new long[idArray.size()];
//			    			for (int j = 0; j < idArray.size(); j++) {
//			    				ids[j] = idArray.get(j);
//			    			}
//			    			
//			    			FragmentManager fm = getActivity().getSupportFragmentManager();
//			    			
//							WishlistDataAddMultiDialogFragment dialogAdd = 
//									WishlistDataAddMultiDialogFragment.newInstance(ids);
//							dialogAdd.setTargetFragment(getThisFragment(), REQUEST_ADD_MULTI);
//							dialogAdd.show(fm, DIALOG_WISHLIST_DATA_ADD_MULTI);
//							
//			    			mode.finish();
//			    			adapter.notifyDataSetChanged();
//			    			return true;
//			    		default:
//			    			return false;
//			    	}
//			    }
//
//			    @Override
//			    public void onDestroyActionMode(ActionMode mode) {		
//			    	// Required, but not used in this implementation
//			    }
//			});
//		}
//	}
//
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//		getActivity().getMenuInflater().inflate(R.menu.context_wishlist_data_add, menu);
//	}
//	
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//		int position = info.position;
//		
//		Weapon weapon = getDetailWeapon(position);
//		long id = weapon.getId();
//		String name = weapon.getName();
//		
//		FragmentManager fm = getActivity().getSupportFragmentManager();
//		
//		switch (item.getItemId()) {
//			case R.id.menu_item_wishlist_add:
//				WishlistDataAddDialogFragment dialogAdd = WishlistDataAddDialogFragment.newInstance(id, name);
//				dialogAdd.setTargetFragment(getThisFragment(), REQUEST_ADD_MULTI);
//				dialogAdd.show(fm, DIALOG_WISHLIST_DATA_ADD_MULTI);
//				return true;
//		}
//		
//		return super.onContextItemSelected(item);
//	}
	
	protected abstract CursorAdapter getDetailAdapter();
	
	protected abstract Weapon getDetailWeapon(int position);
	
	protected abstract Fragment getThisFragment();
}
