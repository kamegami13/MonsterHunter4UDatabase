package com.daviancorp.android.ui.detail;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.ComponentCursor;
import com.daviancorp.android.data.object.Component;
import com.daviancorp.android.loader.ComponentListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ComponentListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String ARG_ITEM_ID = "COMPONENT_ID";

	public static ComponentListFragment newInstance(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, id);
		ComponentListFragment f = new ComponentListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.component_list_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_component_list, null);
		return v;
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long mId = -1;
		if (args != null) {
			mId = args.getLong(ARG_ITEM_ID);
		}
		return new ComponentListCursorLoader(getActivity(), "created", mId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		ComponentListCursorAdapter adapter = new ComponentListCursorAdapter(
				getActivity(), (ComponentCursor) cursor);
		setListAdapter(adapter);

	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Item ID; CursorAdapter gives us this
		// for free
		Intent i = new Intent(getActivity(), ItemDetailActivity.class);
		i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, (long) v.getTag());
		startActivity(i);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	private static class ComponentListCursorAdapter extends CursorAdapter {

		private ComponentCursor mComponentCursor;

		public ComponentListCursorAdapter(Context context, ComponentCursor cursor) {
			super(context, cursor, 0);
			mComponentCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_component_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			Component component = mComponentCursor.getComponent();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);
			ImageView itemImageView = (ImageView) view.findViewById(R.id.item_image);
			TextView itemTextView = (TextView) view.findViewById(R.id.item);
			TextView amtTextView = (TextView) view.findViewById(R.id.amt);
			TextView typeTextView = (TextView) view.findViewById(R.id.type);
			
			String nameText = component.getComponent().getName();
			String amtText = "" + component.getQuantity();
			String typeText = "" + component.getType();
			
			itemTextView.setText(nameText);
			amtTextView.setText(amtText);
			typeTextView.setText(typeText);
			
			Drawable i = null;
			String cellImage = "icons_items/" + component.getComponent().getFileLocation();
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			itemImageView.setImageDrawable(i);

			itemLayout.setTag(component.getComponent().getId());
		}
	}

}
