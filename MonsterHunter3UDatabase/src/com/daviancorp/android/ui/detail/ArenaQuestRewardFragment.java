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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.ArenaRewardCursor;
import com.daviancorp.android.data.object.ArenaReward;
import com.daviancorp.android.loader.ArenaQuestRewardListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ArenaQuestRewardFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_ARENA_QUEST_ID = "ARENA_QUEST_ID";

	public static ArenaQuestRewardFragment newInstance(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ARENA_QUEST_ID, id);
		ArenaQuestRewardFragment f = new ArenaQuestRewardFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.arena_quest_reward_fragment, getArguments(), this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_arena_quest_reward_list, null);
		return v;
	}

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long arenaId = args.getLong(ARG_ARENA_QUEST_ID, -1);
		
		return new ArenaQuestRewardListCursorLoader(getActivity(), "arena", arenaId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor

		ArenaQuestRewardListCursorAdapter adapter = new ArenaQuestRewardListCursorAdapter(
				getActivity(), (ArenaRewardCursor) cursor);
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

	private static class ArenaQuestRewardListCursorAdapter extends CursorAdapter {

		private ArenaRewardCursor mArenaRewardCursor;

		public ArenaQuestRewardListCursorAdapter(Context context, ArenaRewardCursor cursor) {
			super(context, cursor, 0);
			mArenaRewardCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_arena_quest_reward_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			ArenaReward arenaReward = mArenaRewardCursor.getArenaReward();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);
			ImageView itemImageView = (ImageView) view
					.findViewById(R.id.item_image);

			TextView itemTextView = (TextView) view.findViewById(R.id.item);
			TextView amountTextView = (TextView) view.findViewById(R.id.amount);
			TextView percentageTextView = (TextView) view
					.findViewById(R.id.percentage);

			String cellItemText = arenaReward.getItem().getName();
			int cellAmountText = arenaReward.getStackSize();
			int cellPercentageText = arenaReward.getPercentage();

			itemTextView.setText(cellItemText);
			amountTextView.setText("" + cellAmountText);

			String percent = "" + cellPercentageText + "%";
			percentageTextView.setText(percent);

			Drawable i = null;
			String cellImage = "icons_items/" + arenaReward.getItem().getFileLocation();
			
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			itemImageView.setImageDrawable(i);

			itemLayout.setTag(arenaReward.getItem().getId());
		}
	}

}
