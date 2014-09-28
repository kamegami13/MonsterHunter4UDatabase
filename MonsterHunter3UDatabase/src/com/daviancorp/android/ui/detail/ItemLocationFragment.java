package com.daviancorp.android.ui.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.GatheringCursor;
import com.daviancorp.android.data.object.Gathering;
import com.daviancorp.android.loader.GatheringListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ItemLocationFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_ITEM_ID = "ITEM_ID";

	public static ItemLocationFragment newInstance(long itemId) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, itemId);
		ItemLocationFragment f = new ItemLocationFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.item_location_fragment, getArguments(), this);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_item_location_list, null);
		return v;
	}
	

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long itemId = args.getLong(ARG_ITEM_ID, -1);

		return new GatheringListCursorLoader(getActivity(), "item", itemId, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor

		GatheringListCursorAdapter adapter = new GatheringListCursorAdapter(
				getActivity(), (GatheringCursor) cursor);
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
		Intent i = new Intent(getActivity(), LocationDetailActivity.class);
		i.putExtra(LocationDetailActivity.EXTRA_LOCATION_ID, (long) v.getTag());
		startActivity(i);
	}

	private static class GatheringListCursorAdapter extends CursorAdapter {

		private GatheringCursor mGatheringCursor;

		public GatheringListCursorAdapter(Context context,
				GatheringCursor cursor) {
			super(context, cursor, 0);
			mGatheringCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_item_location_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			Gathering gathering = mGatheringCursor.getGathering();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);

			TextView mapTextView = (TextView) view.findViewById(R.id.map);
			TextView rankTextView = (TextView) view.findViewById(R.id.rank);
			TextView areaTextView = (TextView) view.findViewById(R.id.area);
			TextView methodTextView = (TextView) view.findViewById(R.id.method);

			
			String mapName = gathering.getLocation().getName();
			String rank = gathering.getRank();
			String area = gathering.getArea();
			String method = gathering.getSite();
			
			mapTextView.setText(mapName);
			rankTextView.setText(rank);
			areaTextView.setText(area);
			methodTextView.setText(method);
			
			itemLayout.setTag(gathering.getLocation().getId());
		}
	}

}
