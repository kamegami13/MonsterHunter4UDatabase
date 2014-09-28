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

import com.daviancorp.android.data.database.MonsterToQuestCursor;
import com.daviancorp.android.data.object.MonsterToQuest;
import com.daviancorp.android.loader.MonsterToQuestListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class MonsterQuestFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_MONSTER_ID = "MONSTER_ID";

	public static MonsterQuestFragment newInstance(long questId) {
		Bundle args = new Bundle();
		args.putLong(ARG_MONSTER_ID, questId);
		MonsterQuestFragment f = new MonsterQuestFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.monster_quest_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_monster_monstertoquest_list, null);
		return v;
	}

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long monsterId = args.getLong(ARG_MONSTER_ID, -1);

		return new MonsterToQuestListCursorLoader(getActivity(), "monster",
				monsterId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor

		MonsterToQuestListCursorAdapter adapter = new MonsterToQuestListCursorAdapter(
				getActivity(), (MonsterToQuestCursor) cursor);
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
		Intent i = new Intent(getActivity(), QuestDetailActivity.class);
		i.putExtra(QuestDetailActivity.EXTRA_QUEST_ID, (long) v.getTag());
		startActivity(i);
	}

	private static class MonsterToQuestListCursorAdapter extends CursorAdapter {

		private MonsterToQuestCursor mMonsterToQuestCursor;

		public MonsterToQuestListCursorAdapter(Context context,
				MonsterToQuestCursor cursor) {
			super(context, cursor, 0);
			mMonsterToQuestCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_monster_monstertoquest_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			MonsterToQuest monsterToQuest = mMonsterToQuestCursor
					.getMonsterToQuest();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);
			TextView questTextView = (TextView) view
					.findViewById(R.id.quest_name);
			TextView locationTextView = (TextView) view
					.findViewById(R.id.quest_location);
			TextView unstableTextView = (TextView) view
					.findViewById(R.id.quest_unstable);

			String cellQuestText = monsterToQuest.getQuest().getName();
			String cellLocationText = monsterToQuest.getQuest().getHub() + " "
					+ monsterToQuest.getQuest().getStars();
			String cellUnstableText = monsterToQuest.getUnstable();
			
			if (cellUnstableText.equals("no")) {
				cellUnstableText = "";
			}
			else {
				cellUnstableText = "Unstable";
			}
			
			questTextView.setText(cellQuestText);
			locationTextView.setText(cellLocationText);
			unstableTextView.setText(cellUnstableText);

			itemLayout.setTag(monsterToQuest.getQuest().getId());
		}
	}

}
