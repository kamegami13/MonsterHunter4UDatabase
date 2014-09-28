package com.daviancorp.android.ui.list;

import java.io.IOException;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.DecorationCursor;
import com.daviancorp.android.data.object.Decoration;
import com.daviancorp.android.loader.DecorationListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.DecorationDetailActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DecorationListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

//	private static final String DIALOG_WISHLIST_DATA_ADD_MULTI = "wishlist_data_add_multi";
//	private static final int REQUEST_ADD_MULTI = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.decoration_list_fragment, null, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_decoration_list, null);
//		setContextMenu(v);
		return v;
	}

//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		super.onCreateOptionsMenu(menu, inflater);
//		inflater.inflate(R.menu.menu_wishlist_add, menu);
//		
//		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
//		     MenuItem item_down = menu.findItem(R.id.wishlist_add);
//		     item_down.setVisible(false);
//		}
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//			case R.id.wishlist_add:
//				if (getListAdapter().getCount() > 0) {
//					getActivity().startActionMode(new mMultiChoiceModeListener());
//				}
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//			}
//	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		return new DecorationListCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		DecorationListCursorAdapter adapter = new DecorationListCursorAdapter(
				getActivity(), (DecorationCursor) cursor);
		setListAdapter(adapter);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Monster ID; CursorAdapter gives us this
		// for free
		Intent i = new Intent(getActivity(), DecorationDetailActivity.class);
		i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, (long) v.getTag());
		startActivity(i);
	}
	

	private static class DecorationListCursorAdapter extends CursorAdapter {

		private DecorationCursor mDecorationCursor;

		public DecorationListCursorAdapter(Context context,
				DecorationCursor cursor) {
			super(context, cursor, 0);
			mDecorationCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_decoration_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the decoration for the current row
			Decoration decoration = mDecorationCursor.getDecoration();
			
			LinearLayout itemLayout = (LinearLayout) view.findViewById(R.id.listitem);

			// Set up the text view
			ImageView itemImageView = (ImageView) view.findViewById(R.id.item_image);
			TextView decorationNameTextView = (TextView) view.findViewById(R.id.item);
			TextView skill1TextView = (TextView) view.findViewById(R.id.skill1);
			TextView skill1amtTextView = (TextView) view.findViewById(R.id.skill1_amt);
			TextView skill2TextView = (TextView) view.findViewById(R.id.skill2);
			TextView skill2amtTextView = (TextView) view.findViewById(R.id.skill2_amt);
			
			String decorationNameText = decoration.getName();
			String skill1Text = decoration.getSkill1Name();
			String skill1amtText = "" + decoration.getSkill1Point();
			String skill2Text = decoration.getSkill2Name();
			String skill2amtText = "";
			if (decoration.getSkill2Point() != 0) {
				skill2amtText = skill2amtText + decoration.getSkill2Point();
			}

			Drawable i = null;
			String cellImage = "icons_items/" + decoration.getFileLocation();
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			itemImageView.setImageDrawable(i);
			
			decorationNameTextView.setText(decorationNameText);
			skill1TextView.setText(skill1Text);
			skill1amtTextView.setText(skill1amtText);
			skill2TextView.setText(skill2Text);
			skill2amtTextView.setText(skill2amtText);
			
			itemLayout.setTag(decoration.getId());
		}
	}

	/*
	 *  Context menu
	 */

//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//		getActivity().getMenuInflater().inflate(R.menu.context_wishlist_data_add, menu);
//	}
//	
//	protected void setContextMenu(View v) {
//		ListView mListView = (ListView) v.findViewById(android.R.id.list);
//		
//		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//			// Use floating context menus on Froyo and Gingerbread
//			registerForContextMenu(mListView);
//		} else {
//			// Use contextual action bar on Honeycomb and higher
//			mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//			mListView.setMultiChoiceModeListener(new mMultiChoiceModeListener());
//		}
//	}
//	
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//		int position = info.position;
//		
//		DecorationListCursorAdapter adapter = (DecorationListCursorAdapter) getListAdapter();
//		Decoration decoration = ((DecorationCursor) adapter.getItem(position)).getDecoration();
//
//		long id = decoration.getId();
//		String name = decoration.getName();
//		
//		FragmentManager fm = getActivity().getSupportFragmentManager();
//		
//		switch (item.getItemId()) {
//			case R.id.menu_item_wishlist_add:
//				WishlistDataAddDialogFragment dialogAdd = WishlistDataAddDialogFragment.newInstance(id, name);
//				dialogAdd.setTargetFragment(DecorationListFragment.this, REQUEST_ADD_MULTI);
//				dialogAdd.show(fm, DIALOG_WISHLIST_DATA_ADD_MULTI);
//				return true;
//		}
//		
//		return super.onContextItemSelected(item);
//	}
//	
//	private class mMultiChoiceModeListener implements MultiChoiceModeListener {
//		
//		public void onItemCheckedStateChanged(ActionMode mode, int position,
//				long id, boolean checked) {
//			// Required, but not used in this implementation
//		}
//		
//		// ActionMode.Callback methods
//		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//			MenuInflater inflater = mode.getMenuInflater();
//			inflater.inflate(R.menu.context_wishlist_data_add, menu);
//			return true;
//		}
//
//	    @Override
//	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//	        return false;
//	     // Required, but not used in this implementation
//	    }
//
//	    @Override
//	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {					
//	    	switch (item.getItemId()) {
//	    		case R.id.menu_item_wishlist_add:
//	    			DecorationListCursorAdapter adapter = (DecorationListCursorAdapter) getListAdapter();
//	    			ArrayList<Long> idArray = new ArrayList<Long>();
//	    			
//	    			for (int i = 0; i < adapter.getCount(); i++) {
//	    				if (getListView().isItemChecked(i)) {
//	    					idArray.add(((DecorationCursor) adapter.getItem(i)).getDecoration().getId());
//	    				}
//	    			}
//	    			
//	    			long[] ids = new long[idArray.size()];
//	    			for (int j = 0; j < idArray.size(); j++) {
//	    				ids[j] = idArray.get(j);
//	    			}
//	    			
//	    			if (ids.length > 0) {
//		    			FragmentManager fm = getActivity().getSupportFragmentManager();
//		    			
//						WishlistDataAddMultiDialogFragment dialogAdd = 
//								WishlistDataAddMultiDialogFragment.newInstance(ids);
//						dialogAdd.setTargetFragment(DecorationListFragment.this, REQUEST_ADD_MULTI);
//						dialogAdd.show(fm, DIALOG_WISHLIST_DATA_ADD_MULTI);
//	    			}
//	    			
//	    			mode.finish();
//	    			adapter.notifyDataSetChanged();
//	    			return true;
//	    		default:
//	    			return false;
//	    	}
//	    }
//
//	    @Override
//	    public void onDestroyActionMode(ActionMode mode) {		
//	    	// Required, but not used in this implementation
//	    }
//	}
}
