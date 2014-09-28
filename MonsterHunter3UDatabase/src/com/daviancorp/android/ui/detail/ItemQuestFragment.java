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

import com.daviancorp.android.data.database.QuestRewardCursor;
import com.daviancorp.android.data.object.QuestReward;
import com.daviancorp.android.loader.QuestRewardListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ItemQuestFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_ITEM_ID = "ITEM_ID";

	public static ItemQuestFragment newInstance(long itemId) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, itemId);
		ItemQuestFragment f = new ItemQuestFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.item_quest_fragment, getArguments(), this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_item_quest_list, null);
		return v;
	}

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long itemId = args.getLong(ARG_ITEM_ID, -1);

		return new QuestRewardListCursorLoader(getActivity(), "item", itemId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor

		QuestRewardListCursorAdapter adapter = new QuestRewardListCursorAdapter(
				getActivity(), (QuestRewardCursor) cursor);
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

	private static class QuestRewardListCursorAdapter extends CursorAdapter {

		private QuestRewardCursor mQuestRewardCursor;

		public QuestRewardListCursorAdapter(Context context,
				QuestRewardCursor cursor) {
			super(context, cursor, 0);
			mQuestRewardCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_item_quest_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			QuestReward questReward = mQuestRewardCursor.getQuestReward();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);

			TextView questTextView = (TextView) view.findViewById(R.id.quest);
			TextView levelTextView = (TextView) view.findViewById(R.id.level);
			TextView slotTextView = (TextView) view.findViewById(R.id.slot);
			TextView amountTextView = (TextView) view.findViewById(R.id.amount);
			TextView percentageTextView = (TextView) view
					.findViewById(R.id.percentage);

			String cellQuestText = questReward.getQuest().getName();
			String cellLevelText = questReward.getQuest().getHub() + " " + questReward.getQuest().getStars();
			String cellSlotText = questReward.getRewardSlot();
			int cellAmountText = questReward.getStackSize();
			int cellPercentageText = questReward.getPercentage();

			questTextView.setText(cellQuestText);
			levelTextView.setText(cellLevelText);
			slotTextView.setText(cellSlotText);
			amountTextView.setText("" + cellAmountText);

			String percent = "" + cellPercentageText + "%";
			percentageTextView.setText(percent);

			itemLayout.setTag(questReward.getQuest().getId());
		}
	}

}
