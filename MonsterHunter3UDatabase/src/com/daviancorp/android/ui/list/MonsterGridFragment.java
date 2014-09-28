package com.daviancorp.android.ui.list;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.database.MonsterCursor;
import com.daviancorp.android.data.object.Monster;
import com.daviancorp.android.loader.MonsterListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.MonsterDetailActivity;

public class MonsterGridFragment extends Fragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_TAB = "MONSTER_TAB";

	private GridView mGridView;
	private MonsterGridCursorAdapter mAdapter;

	public static MonsterGridFragment newInstance(String tab) {
		Bundle args = new Bundle();
		args.putString(ARG_TAB, tab);
		MonsterGridFragment f = new MonsterGridFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.monster_grid_fragment, getArguments(), this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_monster_grid, parent, false);

		mGridView = (GridView) v.findViewById(R.id.grid_monsters);
		mGridView.setAdapter(mAdapter);
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				Intent i = new Intent(getActivity(), MonsterDetailActivity.class);
				i.putExtra(MonsterDetailActivity.EXTRA_MONSTER_ID, id);
				startActivity(i);
				
			}
		});

		return v;
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
		if (mGridView != null) {
			mGridView.setAdapter(mAdapter);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		mGridView.setAdapter(null);
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
			return inflater.inflate(R.layout.fragment_monster_griditem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the monster for the current row
			Monster monster = mMonsterCursor.getMonster();
	        AssetManager manager = context.getAssets();
	        


			// Set up the text view
			TextView monsterNameTextView = (TextView) view.findViewById(R.id.grid_item_label);
			ImageView monsterImage = (ImageView) view.findViewById(R.id.grid_item_image);
			
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
		}
	}

}
