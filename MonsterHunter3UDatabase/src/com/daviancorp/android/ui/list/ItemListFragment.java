package com.daviancorp.android.ui.list;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.ItemCursor;
import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.loader.ItemListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.ItemDetailActivity;

public class ItemListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private ItemListCursorAdapter mAdapter;
	private String mFilter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.item_list_fragment, null, this);
		
		mFilter = "";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list_search, null);
		
		EditText inputSearch = (EditText) v.findViewById(R.id.input_search);
		inputSearch.addTextChangedListener(new TextWatcher() {
		     
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // When user changed the Text
		    	mFilter = cs.toString();
		    	getLoaderManager().restartLoader(0, null, ItemListFragment.this);
		    }
		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {
		        // TODO Auto-generated method stub
		         
		    }
		     
		    @Override
		    public void afterTextChanged(Editable arg0) {
		        // TODO Auto-generated method stub                          
		    }
		});
		
		return v;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		return new ItemListCursorLoader(getActivity(), mFilter);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		mAdapter = new ItemListCursorAdapter(getActivity(), (ItemCursor) cursor);
		setListAdapter(mAdapter);
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
		Intent i = new Intent(getActivity(), ItemDetailActivity.class);
		i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, id);
		startActivity(i);
	}

	private static class ItemListCursorAdapter extends CursorAdapter {

		private ItemCursor mItemCursor;

		public ItemListCursorAdapter(Context context, ItemCursor cursor) {
			super(context, cursor, 0);
			mItemCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			return inflater.inflate(R.layout.fragment_item_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			Item item = mItemCursor.getItem();

			// Set up the text view
			TextView itemNameTextView = (TextView) view
					.findViewById(R.id.text1);
			ImageView itemImageView = (ImageView) view
					.findViewById(R.id.icon);

			String cellText = item.getName();
			String cellImage = "icons_items/" + item.getFileLocation();

			itemNameTextView.setText(cellText);

			Drawable itemImage = null;

			try {
				itemImage = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			itemImageView.setImageDrawable(itemImage);

		}
	}

}
