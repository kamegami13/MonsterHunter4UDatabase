package com.daviancorp.android.ui.list;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Monster;
import com.daviancorp.android.data.database.MonsterCursor;
import com.daviancorp.android.loader.MonsterListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.MonsterClickListener;

import java.io.IOException;
import java.io.InputStream;

public class MonsterListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_TAB = "MONSTER_TAB";

	private MonsterGridCursorAdapter mAdapter;

	public static MonsterListFragment newInstance(String tab) {
		Bundle args = new Bundle();
		args.putString(ARG_TAB, tab);
		MonsterListFragment f = new MonsterListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        int id = 0;

        switch(getArguments().getString("MONSTER_TAB")) {
            case "Large":
                id = R.id.monster_large_loader;
                break;
            case "Small":
                id = R.id.monster_small_loader;
                break;
            default:
                id = R.id.monster_all_loader;
                break;
        }

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(id, getArguments(), this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {

        return inflater
                .inflate(R.layout.fragment_generic_list, parent, false);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		String mTab = null;
		if (args != null) {
			mTab = args.getString(ARG_TAB);
		}

		return new MonsterListCursorLoader(getActivity(), mTab);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		mAdapter = new MonsterGridCursorAdapter(getActivity(),
				(MonsterCursor) cursor);
		setListAdapter(mAdapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(mAdapter);
	}
	

	private static class MonsterGridCursorAdapter extends CursorAdapter {

		private MonsterCursor mMonsterCursor;

		public MonsterGridCursorAdapter(Context context, MonsterCursor cursor) {
			super(context, cursor, 0);
			mMonsterCursor = cursor;
		}


		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_list_item_basic,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the monster for the current row
			Monster monster = mMonsterCursor.getMonster();
	        AssetManager manager = context.getAssets();

			// Set up the text view
			TextView monsterNameTextView = (TextView) view.findViewById(R.id.item_label);
			ImageView monsterImage = (ImageView) view.findViewById(R.id.item_image);

            RelativeLayout itemLayout = (RelativeLayout) view.findViewById(R.id.listitem);
			
			String cellText = monster.getName();
			String cellImage = "icons_monster/" + monster.getFileLocation();
			
			monsterNameTextView.setText(cellText);
			
	        // Read a Bitmap from Assets
	        try {
	            InputStream open = manager.open(cellImage);
	            Bitmap bitmap = BitmapFactory.decodeStream(open);
	            // Assign the bitmap to an ImageView in this layout
	            monsterImage.setImageBitmap(bitmap);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

            itemLayout.setTag(monster.getId());
            itemLayout.setOnClickListener(new MonsterClickListener(context, monster.getId()));
		}
	}

}
