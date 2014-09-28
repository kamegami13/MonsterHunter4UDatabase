package com.daviancorp.android.ui.detail;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.WishlistDataCursor;
import com.daviancorp.android.data.object.WishlistData;
import com.daviancorp.android.loader.WishlistDataListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.dialog.WishlistDataDeleteDialogFragment;
import com.daviancorp.android.ui.dialog.WishlistDataEditDialogFragment;

public class WishlistDataDetailFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	public static final String EXTRA_DETAIL_REFRESH =
			"com.daviancorp.android.ui.general.wishlist_detail_refresh";
	
	private static final String ARG_ID = "ID";

	private static final String DIALOG_WISHLIST_DATA_EDIT = "wishlist_data_edit";
	private static final String DIALOG_WISHLIST_DATA_DELETE = "wishlist_data_delete";
	private static final int REQUEST_REFRESH = 0;
	private static final int REQUEST_EDIT = 1;
	private static final int REQUEST_DELETE = 2;

	private boolean started, fromOtherTab;
	
	private ListView mListView;
	private TextView mHeaderTextView, mItemTypeTextView, mQuantityTypeTextView, mExtraTypeTextView;
	private ActionMode mActionMode;
	
	public static WishlistDataDetailFragment newInstance(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ID, id);
		WishlistDataDetailFragment f = new WishlistDataDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		
		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.wishlist_data_detail_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_wishlist_component_list, container, false);

		mListView = (ListView) v.findViewById(android.R.id.list);
		mHeaderTextView = (TextView) v.findViewById(R.id.header);
		mItemTypeTextView = (TextView) v.findViewById(R.id.item_type);
		mQuantityTypeTextView = (TextView) v.findViewById(R.id.quantity_type);
		mExtraTypeTextView = (TextView) v.findViewById(R.id.extra_type);

		mHeaderTextView.setText("Red highlight = Can make one");
		mHeaderTextView.setTextColor(Color.RED);
		
		mItemTypeTextView.setText("Item");
		mQuantityTypeTextView.setText("Quantity");
		mExtraTypeTextView.setText("Method");
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			// Use floating context menus on Froyo and Gingerbread
			registerForContextMenu(mListView);
		} else {
			// Use contextual action bar on Honeycomb and higher
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			        @Override
			        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			            if (mActionMode != null) {
			                return false;
			            }
			
			            mActionMode = getActivity().startActionMode(new mActionModeCallback());
			            mActionMode.setTag(position);
			            mListView.setItemChecked(position, true);
			            return true;
			        }
			});
		}
		
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_wishlist_edit, menu);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
		     MenuItem item_down = menu.findItem(R.id.wishlist_edit);
		     item_down.setVisible(false);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.wishlist_edit:
				if (mListView.getAdapter().getCount() > 0) {
					mActionMode = getActivity().startActionMode(new mActionModeCallback());
		            mActionMode.setTag(0);
					mListView.setItemChecked(0, true);
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getActivity().getMenuInflater().inflate(R.menu.context_wishlist_data, menu);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_REFRESH) {
			if(data.getBooleanExtra(WishlistDataComponentFragment.EXTRA_COMPONENT_REFRESH, false)) {
				fromOtherTab = true;
				updateUI();
			}
		}
		else if (requestCode == REQUEST_EDIT) {
			if(data.getBooleanExtra(WishlistDataEditDialogFragment.EXTRA_EDIT, false)) {
				updateUI();
			}
		}
		else if (requestCode == REQUEST_DELETE) {
			if(data.getBooleanExtra(WishlistDataDeleteDialogFragment.EXTRA_DELETE, false)) {
				updateUI();
			}
		}
	}
	
	private void updateUI() {
		if (started) {
			getLoaderManager().getLoader( R.id.wishlist_data_detail_fragment ).forceLoad();
			WishlistDataListCursorAdapter adapter = (WishlistDataListCursorAdapter) getListAdapter();
			adapter.notifyDataSetChanged();
	
			if (!fromOtherTab) {
				sendResult(Activity.RESULT_OK, true);
			}
			else {
				fromOtherTab = false;
			}
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}
	
	private void sendResult(int resultCode, boolean refresh) {
		if (getTargetFragment() == null) {
			return;
		}

		Intent i = new Intent();
		i.putExtra(EXTRA_DETAIL_REFRESH, refresh);
		
		getTargetFragment()
			.onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;
		
		boolean temp = onItemSelected(item, position);
		
		if(temp) {
			return true;
		}
		else {
			return super.onContextItemSelected(item);
		}
	}
	
	private boolean onItemSelected(MenuItem item, int position) {
		WishlistDataListCursorAdapter adapter = (WishlistDataListCursorAdapter) getListAdapter();
		WishlistData wishlistData = ((WishlistDataCursor) adapter.getItem(position)).getWishlistData();
		long id = wishlistData.getId();
		String name = wishlistData.getItem().getName();
		
		FragmentManager fm = getActivity().getSupportFragmentManager();
		
		switch (item.getItemId()) {
			case R.id.menu_item_edit_wishlist_data:
				WishlistDataEditDialogFragment dialogEdit = WishlistDataEditDialogFragment.newInstance(id, name);
				dialogEdit.setTargetFragment(WishlistDataDetailFragment.this, REQUEST_EDIT);
				dialogEdit.show(fm, DIALOG_WISHLIST_DATA_EDIT);
				return true;
			case R.id.menu_item_delete_wishlist_data:
				WishlistDataDeleteDialogFragment dialogDelete = WishlistDataDeleteDialogFragment.newInstance(id, name);
				dialogDelete.setTargetFragment(WishlistDataDetailFragment.this, REQUEST_DELETE);
				dialogDelete.show(fm, DIALOG_WISHLIST_DATA_DELETE);
				return true;
			default:
				return false;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Item ID; CursorAdapter gives us this
		// for free
		if (mActionMode == null) {
            mListView.setItemChecked(position, false);
			Intent i = null;
			long mId = (long) v.getTag();
			
			if (mId < 1118) {
				i = new Intent(getActivity(), ItemDetailActivity.class);
				i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, mId);
			}
			else if (mId < 1314) {
				i = new Intent(getActivity(), DecorationDetailActivity.class);
				i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, mId);
			}
			else if (mId < 2955) {
				i = new Intent(getActivity(), ArmorDetailActivity.class);
				i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, mId);
			}
			else {
				i = new Intent(getActivity(), WeaponDetailActivity.class);
				i.putExtra(WeaponDetailActivity.EXTRA_WEAPON_ID, mId);
			}
			startActivity(i);
		}
		// Contextual action bar options
		else { 
            mActionMode.setTag(position);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long mId = -1;
		if (args != null) {
			mId = args.getLong(ARG_ID);
		}
		return new WishlistDataListCursorLoader(getActivity(), mId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		WishlistDataListCursorAdapter adapter = new WishlistDataListCursorAdapter(
				getActivity(), (WishlistDataCursor) cursor);
		setListAdapter(adapter);

		started = true;
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	private static class WishlistDataListCursorAdapter extends CursorAdapter {

		private WishlistDataCursor mWishlistDataCursor;

		public WishlistDataListCursorAdapter(Context context, WishlistDataCursor cursor) {
			super(context, cursor, 0);
			mWishlistDataCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_wishlist_data_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			WishlistData data = mWishlistDataCursor.getWishlistData();

			// Set up the text view
			LinearLayout root = (LinearLayout) view.findViewById(R.id.listitem);
			ImageView itemImageView = (ImageView) view.findViewById(R.id.item_image);
			TextView itemTextView = (TextView) view.findViewById(R.id.item);
			TextView amtTextView = (TextView) view.findViewById(R.id.amt);
			TextView extraTextView = (TextView) view.findViewById(R.id.extra);
			
			long id = data.getItem().getId();
			String nameText = data.getItem().getName();
			String amtText = "" + data.getQuantity();
			
			String extraText = data.getPath();
			int satisfied = data.getSatisfied();

			itemTextView.setTextColor(Color.BLACK);
			if (satisfied == 1) {
				itemTextView.setTextColor(Color.RED);
			}
			
			
			itemTextView.setText(nameText);
			amtTextView.setText(amtText);
			extraTextView.setText(extraText);
			
			Drawable i = null;
			String cellImage = "";
			String cellRare = "" + data.getItem().getRarity();
			
			if (id < 1314) {
				cellImage = "icons_items/" + data.getItem().getFileLocation();
			} 
			else if ((id >= 1314) && (id < 1646)) {
				cellImage = "icons_armor/icons_head/head" + cellRare + ".png";
			}
			else if ((id >= 1646) && (id < 1983)) {
				cellImage = "icons_armor/icons_body/body" + cellRare + ".png";
			}
			else if ((id >= 1983) && (id < 2303)) {
				cellImage = "icons_armor/icons_arms/arms" + cellRare + ".png";
			}
			else if ((id >= 2303) && (id < 2623)) {
				cellImage = "icons_armor/icons_waist/waist" + cellRare + ".png";
			}
			else if ((id >= 2623) && (id < 2955)) {
				cellImage = "icons_armor/icons_legs/legs" + cellRare + ".png";
			}
			else if ((id >= 2955) && (id < 3091)) {
				cellImage = "icons_weapons/icons_great_sword/great_sword" + cellRare + ".png";
			}
			else if ((id >= 3091) && (id < 3191)) {
				cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + cellRare + ".png";
			}
			else if ((id >= 3191) && (id < 3305)) {
				cellImage = "icons_weapons/icons_long_sword/long_sword" + cellRare + ".png";
			}
			else if ((id >= 3305) && (id < 3445)) {
				cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + cellRare + ".png";
			}
			else if ((id >= 3445) && (id < 3570)) {
				cellImage = "icons_weapons/icons_dual_blades/dual_blades" + cellRare + ".png";
			}
			else if ((id >= 3570) && (id < 3704)) {
				cellImage = "icons_weapons/icons_hammer/hammer" + cellRare + ".png";
			}
			else if ((id >= 3704) && (id < 3849)) {
				cellImage = "icons_weapons/icons_lance/lance" + cellRare + ".png";
			}
			else if ((id >= 3849) && (id < 3961)) {
				cellImage = "icons_weapons/icons_gunlance/gunlance" + cellRare + ".png";
			}
			else if ((id >= 3961) && (id < 4074)) {
				cellImage = "icons_weapons/icons_switch_axe/switch_axe" + cellRare + ".png";
			}
			else if ((id >= 4074) && (id < 4170)) {
				cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + cellRare + ".png";
			}
			else if ((id >= 4170) && (id < 4261)) {
				cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + cellRare + ".png";
			}
			else if (id >= 4261) {
				cellImage = "icons_weapons/icons_bow/bow" + cellRare + ".png";
			}
			
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			itemImageView.setImageDrawable(i);

			root.setTag(id);
		}
	}

	
	private class mActionModeCallback implements ActionMode.Callback {
	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.context_wishlist_data, menu);
	        return true;
	    }

	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false;
	    }

	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    	int position = Integer.parseInt(mode.getTag().toString());
	    	mode.finish();
	    	return onItemSelected(item, position);
	    }

	    @Override
	    public void onDestroyActionMode(ActionMode mode) {		        
	        for (int i = 0; i < mListView.getCount(); i++) {
	        	mListView.setItemChecked(i, false);
	        }

	        mActionMode = null;
	    }
	}
}
