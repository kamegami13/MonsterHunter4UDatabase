package com.daviancorp.android.ui.detail;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.MonsterToArenaCursor;
import com.daviancorp.android.data.object.MonsterToArena;
import com.daviancorp.android.loader.MonsterToArenaListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ArenaQuestMonsterFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_ARENA_QUEST_ID = "ARENA_QUEST_ID";

	public static ArenaQuestMonsterFragment newInstance(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ARENA_QUEST_ID, id);
		ArenaQuestMonsterFragment f = new ArenaQuestMonsterFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.arena_quest_monster_fragment, getArguments(), this);
	}

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long arenaId = args.getLong(ARG_ARENA_QUEST_ID, -1);

		return new MonsterToArenaListCursorLoader(getActivity(), "arena",
				arenaId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor

		MonsterToArenaListCursorAdapter adapter = new MonsterToArenaListCursorAdapter(
				getActivity(), (MonsterToArenaCursor) cursor);
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
		Intent i = new Intent(getActivity(), MonsterDetailActivity.class);
		i.putExtra(MonsterDetailActivity.EXTRA_MONSTER_ID, (long) v.getTag());
		startActivity(i);
	}

	private static class MonsterToArenaListCursorAdapter extends CursorAdapter {

		private MonsterToArenaCursor mMonsterToArenaCursor;

		public MonsterToArenaListCursorAdapter(Context context,
				MonsterToArenaCursor cursor) {
			super(context, cursor, 0);
			mMonsterToArenaCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_arena_quest_monster,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			MonsterToArena monsterToArena = mMonsterToArenaCursor
					.getMonsterToArena();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);
			ImageView monsterImageView = (ImageView) view
					.findViewById(R.id.detail_monster_image);
			TextView monsterTextView = (TextView) view
					.findViewById(R.id.detail_monster_label);
			
			String cellMonsterText = monsterToArena.getMonster().getName();
			String cellTraitText = monsterToArena.getMonster().getTrait(); 
			
			if (!cellTraitText.equals("")) {
				cellMonsterText = cellMonsterText + " (" + cellTraitText + ")";
			}
			
			monsterTextView.setText(cellMonsterText);

			Drawable i = null;
			String cellImage = "icons_monster/"
					+ monsterToArena.getMonster().getFileLocation();
			Log.d("heyo1", cellImage);
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			monsterImageView.setImageDrawable(i);

			itemLayout.setTag(monsterToArena.getMonster().getId());
		}
	}

}
