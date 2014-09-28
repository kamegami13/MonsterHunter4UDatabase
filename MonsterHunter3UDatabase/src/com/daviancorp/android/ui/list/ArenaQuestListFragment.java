package com.daviancorp.android.ui.list;

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
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.ArenaQuestCursor;
import com.daviancorp.android.data.object.ArenaQuest;
import com.daviancorp.android.loader.ArenaQuestListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.ArenaQuestDetailActivity;

public class ArenaQuestListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.arena_quest_list_fragment, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		return new ArenaQuestListCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		ArenaQuestListCursorAdapter adapter = new ArenaQuestListCursorAdapter(
				getActivity(), (ArenaQuestCursor) cursor);
		setListAdapter(adapter);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Arena Quest ID; CursorAdapter gives us this for free
		Intent i = new Intent(getActivity(), ArenaQuestDetailActivity.class);
		i.putExtra(ArenaQuestDetailActivity.EXTRA_ARENA_QUEST_ID, id);
		startActivity(i);
	}

	private static class ArenaQuestListCursorAdapter extends CursorAdapter {

		private ArenaQuestCursor mArenaQuestCursor;

		public ArenaQuestListCursorAdapter(Context context,
				ArenaQuestCursor cursor) {
			super(context, cursor, 0);
			mArenaQuestCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_arena_quest_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			ArenaQuest arenaQuest = mArenaQuestCursor.getArenaQuest();

			// Set up the text view
			TextView mainTextView = (TextView) view.findViewById(R.id.item);
			String cellText = arenaQuest.getName();
			mainTextView.setText(cellText);
			
			
		}
	}

}
