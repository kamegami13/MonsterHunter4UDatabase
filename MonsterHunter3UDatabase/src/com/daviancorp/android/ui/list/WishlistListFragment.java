package com.daviancorp.android.ui.list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ListView;
import android.widget.TextView;
import com.daviancorp.android.data.database.WishlistCursor;
import com.daviancorp.android.data.object.Wishlist;
import com.daviancorp.android.loader.WishlistListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.WishlistDetailActivity;
import com.daviancorp.android.ui.dialog.WishlistAddDialogFragment;
import com.daviancorp.android.ui.dialog.WishlistCopyDialogFragment;
import com.daviancorp.android.ui.dialog.WishlistDeleteDialogFragment;
import com.daviancorp.android.ui.dialog.WishlistRenameDialogFragment;

@SuppressLint("NewApi")
public class WishlistListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String DIALOG_WISHLIST_ADD = "wishlist_add";
	private static final String DIALOG_WISHLIST_RENAME = "wishlist_rename";
	private static final String DIALOG_WISHLIST_COPY = "wishlist_copy";
	private static final String DIALOG_WISHLIST_DELETE = "wishlist_delete";
	private static final int REQUEST_ADD = 0;
	private static final int REQUEST_RENAME = 1;
	private static final int REQUEST_COPY = 2;
	private static final int REQUEST_DELETE = 3;
	
	private ActionMode mActionMode;
	private ListView mListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.wishlist_list_fragment, null, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list_generic, container, false);
		
		mListView = (ListView) v.findViewById(android.R.id.list);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			// Use floating context menus on Froyo and Gingerbread
			registerForContextMenu(mListView);
		} else {
			// Use contextual action bar on Honeycomb and higher
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			        @SuppressLint("NewApi")
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
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		return new WishlistListCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		WishlistListCursorAdapter adapter = new WishlistListCursorAdapter(
				getActivity(), (WishlistCursor) cursor);
		setListAdapter(adapter);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_wishlist_list, menu);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
		     MenuItem item_down = menu.findItem(R.id.wishlist_add);
		     item_down.setVisible(false);
		}
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.wishlist_add:
				FragmentManager fm = getActivity().getSupportFragmentManager();
				WishlistAddDialogFragment dialog = new WishlistAddDialogFragment();
				dialog.setTargetFragment(WishlistListFragment.this, REQUEST_ADD);
				dialog.show(fm, DIALOG_WISHLIST_ADD);
				
				return true;
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
		getActivity().getMenuInflater().inflate(R.menu.context_wishlist, menu);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_ADD) {
			if(data.getBooleanExtra(WishlistAddDialogFragment.EXTRA_ADD, false)) {
				updateUI();
			}
		}
		else if (requestCode == REQUEST_RENAME) {
			if(data.getBooleanExtra(WishlistRenameDialogFragment.EXTRA_RENAME, false)) {
				updateUI();
			}
		}
		else if (requestCode == REQUEST_COPY) {
			if(data.getBooleanExtra(WishlistCopyDialogFragment.EXTRA_COPY, false)) {
				updateUI();
			}
		}
		else if (requestCode == REQUEST_DELETE) {
			if(data.getBooleanExtra(WishlistDeleteDialogFragment.EXTRA_DELETE, false)) {
				updateUI();
			}
		}
	}
	
	private void updateUI() {
		getLoaderManager().getLoader( R.id.wishlist_list_fragment ).forceLoad();
		WishlistListCursorAdapter adapter = (WishlistListCursorAdapter) getListAdapter();
		adapter.notifyDataSetChanged();
		
	}
	
	private boolean onItemSelected(MenuItem item, int position) {
		WishlistListCursorAdapter adapter = (WishlistListCursorAdapter) getListAdapter();
		Wishlist wishlist = ((WishlistCursor) adapter.getItem(position)).getWishlist();
		long id = wishlist.getId();
		String name = wishlist.getName();
		
		FragmentManager fm = getActivity().getSupportFragmentManager();
		
		switch (item.getItemId()) {
			case R.id.menu_item_rename_wishlist:
				WishlistRenameDialogFragment dialogRename = WishlistRenameDialogFragment.newInstance(id, name);
				dialogRename.setTargetFragment(WishlistListFragment.this, REQUEST_RENAME);
				dialogRename.show(fm, DIALOG_WISHLIST_RENAME);
				return true;
			case R.id.menu_item_copy_wishlist:
				WishlistCopyDialogFragment dialogCopy = WishlistCopyDialogFragment.newInstance(id, name);
				dialogCopy.setTargetFragment(WishlistListFragment.this, REQUEST_COPY);
				dialogCopy.show(fm, DIALOG_WISHLIST_COPY);
				return true;
			case R.id.menu_item_delete_wishlist:
				WishlistDeleteDialogFragment dialogDelete = WishlistDeleteDialogFragment.newInstance(id, name);
				dialogDelete.setTargetFragment(WishlistListFragment.this, REQUEST_DELETE);
				dialogDelete.show(fm, DIALOG_WISHLIST_DELETE);
				return true;
			default:
				return false;
		}
	}
	
	private class mActionModeCallback implements ActionMode.Callback {
	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.context_wishlist, menu);
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
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Skill ID; CursorAdapter gives us this for free
		if (mActionMode == null) {
            mListView.setItemChecked(position, false);
			Intent i = new Intent(getActivity(), WishlistDetailActivity.class);
			i.putExtra(WishlistDetailActivity.EXTRA_WISHLIST_ID, id);
			startActivity(i);
		} 
		// Contextual action bar options
		else { 
            mActionMode.setTag(position);
		}
	}

	private static class WishlistListCursorAdapter extends CursorAdapter {

		private WishlistCursor mWishlistCursor;

		public WishlistListCursorAdapter(Context context,
				WishlistCursor cursor) {
			super(context, cursor, 0);
			mWishlistCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_list_view_generic,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			Wishlist wishlist = mWishlistCursor.getWishlist();

			// Set up the text view
			TextView wishlistNameTextView = (TextView) view;
			String cellText = wishlist.getName();
			wishlistNameTextView.setText(cellText);
			
		}
	}

}
