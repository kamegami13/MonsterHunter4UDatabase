package com.daviancorp.android.ui.list;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;
import androidx.cursoradapter.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Combining;
import com.daviancorp.android.data.database.CombiningCursor;
import com.daviancorp.android.loader.CombiningListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.ItemClickListener;

import java.io.IOException;

public class CombiningListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.combining_list_fragment, getArguments(), this);
	}

    public static CombiningListFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong("ID", id);
        CombiningListFragment f = new CombiningListFragment();
        f.setArguments(args);
        return f;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_generic_list, container,false);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		return new CombiningListCursorLoader(getActivity(),
                args.getLong("ID"));
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		CombiningListCursorAdapter adapter = new CombiningListCursorAdapter(
				getActivity(), (CombiningCursor) cursor);
		setListAdapter(adapter);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	private static class CombiningListCursorAdapter extends CursorAdapter {

		private CombiningCursor mCombiningCursor;

		public CombiningListCursorAdapter(Context context,
				CombiningCursor cursor) {
			super(context, cursor, 0);
			mCombiningCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			return inflater
					.inflate(R.layout.fragment_combining_listitem, parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {

			// Get the combination for the current row
			Combining item = mCombiningCursor.getCombining();
			View v = view;

			String item1 = item.getItem1().getName();
			String item2 = item.getItem2().getName();
			String item3 = item.getCreatedItem().getName();

			String cellImage1 = "icons_items/" + item.getItem1().getFileLocation();
			String cellImage2 = "icons_items/" + item.getItem2().getFileLocation();
			String cellImage3 = "icons_items/" + item.getCreatedItem().getFileLocation();

			int percent = item.getPercentage();
			int min = item.getAmountMadeMin();
			int max = item.getAmountMadeMax();

			String temp = "" + min;

			if (min != max){
				temp = temp + "-" + max;
			}

			String percentage = "" + percent + "%";

			TextView itemtv1 = (TextView) v.findViewById(R.id.item_text1);
			TextView itemtv2 = (TextView) v.findViewById(R.id.item_text2);
			TextView itemtv3 = (TextView) v.findViewById(R.id.item_text3);

			ImageView itemiv1 = (ImageView) v.findViewById(R.id.item_img1);
			ImageView itemiv2 = (ImageView) v.findViewById(R.id.item_img2);
			ImageView itemiv3 = (ImageView) v.findViewById(R.id.item_img3);

			LinearLayout itemlayout1 = (LinearLayout) v.findViewById(R.id.item1);
			LinearLayout itemlayout2 = (LinearLayout) v.findViewById(R.id.item2);
			RelativeLayout itemlayout3 = (RelativeLayout) v.findViewById(R.id.item3);

			TextView percenttv = (TextView) v.findViewById(R.id.percentage);
			TextView amttv = (TextView) v.findViewById(R.id.amt);

			Drawable i1 = null;
			Drawable i2 = null;
			Drawable i3 = null;

			try {
				i1 = Drawable.createFromStream(context.getAssets()
						.open(cellImage1), null);
				i2 = Drawable.createFromStream(context.getAssets()
						.open(cellImage2), null);
				i3 = Drawable.createFromStream(context.getAssets()
						.open(cellImage3), null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			itemiv1.setImageDrawable(i1);
			itemiv2.setImageDrawable(i2);
			itemiv3.setImageDrawable(i3);

			percenttv.setText(percentage);
			amttv.setText(temp);

			itemtv1.setText(item1);
			itemtv2.setText(item2);
			itemtv3.setText(item3);

			itemlayout1.setOnClickListener(new ItemClickListener(context, item.getItem1().getId()));
			itemlayout2.setOnClickListener(new ItemClickListener(context, item.getItem2().getId()));
			itemlayout3.setOnClickListener(new ItemClickListener(context, item.getCreatedItem().getId()));

		}

		@Override
		public boolean isEnabled(int position) {
			return false;
		}

	}

}
