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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.HuntingFleetCursor;
import com.daviancorp.android.data.object.HuntingFleet;
import com.daviancorp.android.loader.HuntingFleetListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.ItemDetailActivity;

public class HuntingFleetListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_TYPE = "HUNTINGFLEET_TYPE";
	private static final String ARG_LOCATION = "HUNTINGFLEET_LOCATION";

	public static HuntingFleetListFragment newInstance(String type,
			String location) {
		Bundle args = new Bundle();
		args.putString(ARG_TYPE, type);
		args.putString(ARG_LOCATION, location);
		HuntingFleetListFragment f = new HuntingFleetListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.hunting_fleet_list_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_huntingfleet_list, null);
		return v;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		String mType = null;
		String mLocation = null;
		if (args != null) {
			mType = args.getString(ARG_TYPE);
			mLocation = args.getString(ARG_LOCATION);
		}

		return new HuntingFleetListCursorLoader(getActivity(), mType, mLocation);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		HuntingFleetListCursorAdapter adapter = new HuntingFleetListCursorAdapter(
				getActivity(), (HuntingFleetCursor) cursor);
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
		Intent i = new Intent(getActivity(), ItemDetailActivity.class);
		i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, (long) v.getTag());
		startActivity(i);
	}

	private static class HuntingFleetListCursorAdapter extends CursorAdapter {

		private HuntingFleetCursor mHuntingFleetCursor;

		public HuntingFleetListCursorAdapter(Context context,
				HuntingFleetCursor cursor) {
			super(context, cursor, 0);
			mHuntingFleetCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_huntingfleet_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the hunting fleet for the current row
			HuntingFleet huntingfleet = mHuntingFleetCursor.getHuntingFleet();
			
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);
			ImageView itemImageView = (ImageView) view
					.findViewById(R.id.item_image);

			TextView itemTextView = (TextView) view.findViewById(R.id.item);
			TextView locationTextView = (TextView) view.findViewById(R.id.location);
			TextView amountTextView = (TextView) view.findViewById(R.id.amount);
			TextView percentageTextView = (TextView) view
					.findViewById(R.id.percentage);
			TextView levelTextView = (TextView) view.findViewById(R.id.level);

			String cellItemText = huntingfleet.getItem().getName();
			String cellLocationText = huntingfleet.getLocation();
			int cellAmountText = huntingfleet.getAmount();
			int cellPercentageText = huntingfleet.getPercentage();
			int cellLevelText = huntingfleet.getLevel();

			itemTextView.setText(cellItemText);
			locationTextView.setText(cellLocationText);
			amountTextView.setText("" + cellAmountText);

			String percent = "" + cellPercentageText + "%";
			percentageTextView.setText(percent);
			
			levelTextView.setText("" + cellLevelText);

			Drawable i = null;
			String cellImage = "icons_items/" + huntingfleet.getItem().getFileLocation();
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			itemImageView.setImageDrawable(i);

			itemLayout.setTag(huntingfleet.getItem().getId());
		}
	}

}
