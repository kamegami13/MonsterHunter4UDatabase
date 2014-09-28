package com.daviancorp.android.ui.detail;

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

import com.daviancorp.android.data.database.HuntingRewardCursor;
import com.daviancorp.android.data.object.HuntingReward;
import com.daviancorp.android.loader.HuntingRewardListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ItemMonsterFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_ITEM_ID = "ITEM_ID";
	
	public static ItemMonsterFragment newInstance(long itemId) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, itemId);
		ItemMonsterFragment f = new ItemMonsterFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.item_monster_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_item_monster_list, null);
		return v;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long itemId = args.getLong(ARG_ITEM_ID, -1);
		
		return new HuntingRewardListCursorLoader(getActivity(), "item", itemId, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		ItemHuntingRewardListCursorAdapter adapter = new ItemHuntingRewardListCursorAdapter(
				getActivity(), (HuntingRewardCursor) cursor);
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
		
		long monsterId = (long) v.getTag();
		
		if (monsterId == 2) {
			monsterId = 1;
		}
		else if (monsterId == 4) {
			monsterId = 3;
		}
		else if ((monsterId >=6) && (monsterId <= 10)) {
			monsterId = 5;
		}
		else if ((monsterId >= 12) && (monsterId <= 16)) {
			monsterId = 11;
		}
		else if ((monsterId >= 18) && (monsterId <= 23)) {
			monsterId = 17;
		}
		
		Intent i = new Intent(getActivity(), MonsterDetailActivity.class);
		i.putExtra(MonsterDetailActivity.EXTRA_MONSTER_ID, monsterId);
		startActivity(i);
	}

	private static class ItemHuntingRewardListCursorAdapter extends CursorAdapter {

		private HuntingRewardCursor mHuntingRewardCursor;

		public ItemHuntingRewardListCursorAdapter(Context context, HuntingRewardCursor cursor) {
			super(context, cursor, 0);
			mHuntingRewardCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_item_monster_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			HuntingReward huntingReward = mHuntingRewardCursor.getHuntingReward();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view.findViewById(R.id.listitem);
			
			TextView rankTextView = (TextView) view.findViewById(R.id.rank);
			TextView monsterTextView = (TextView) view.findViewById(R.id.monster);
			TextView methodTextView = (TextView) view.findViewById(R.id.method);
			TextView amountTextView = (TextView) view.findViewById(R.id.amount);
			TextView percentageTextView = (TextView) view
					.findViewById(R.id.percentage);

			String cellRankText = huntingReward.getRank();
			String cellMonsterText = huntingReward.getMonster().getName();
			String cellTraitText = huntingReward.getMonster().getTrait(); 
			String cellMethodText = huntingReward.getCondition();
			int cellAmountText = huntingReward.getStackSize();
			int cellPercentageText = huntingReward.getPercentage();

			if (!cellTraitText.equals("")) {
				cellMonsterText = cellMonsterText + " (" + cellTraitText + ")";
			}
			
			rankTextView.setText(cellRankText);
			monsterTextView.setText(cellMonsterText);
			methodTextView.setText(cellMethodText);
			amountTextView.setText("" + cellAmountText);

			String percent = "" + cellPercentageText + "%";
			percentageTextView.setText(percent);

			itemLayout.setTag(huntingReward.getMonster().getId());
			
			
			
//			TextView itemNameTextView = (TextView) view;
//			String cellText = huntingReward.getMonster().getName() + "\t\t\t\t" + huntingReward.getLocation();
//			itemNameTextView.setText(cellText);
//			
//			itemNameTextView.setTag(huntingReward.getMonster().getId());
			
			
		}
	}

}
