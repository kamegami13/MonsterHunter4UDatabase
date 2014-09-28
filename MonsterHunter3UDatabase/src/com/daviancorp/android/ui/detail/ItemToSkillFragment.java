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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daviancorp.android.data.database.ItemToSkillTreeCursor;
import com.daviancorp.android.data.object.ItemToSkillTree;
import com.daviancorp.android.loader.ItemToSkillTreeListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ItemToSkillFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_ITEM_TO_SKILL_ID = "ITEM_TO_SKILL_ID";
	private static final String ARG_ITEM_TO_SKILL_FROM = "ITEM_TO_SKILL_FROM";

	public static ItemToSkillFragment newInstance(long id, String from) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_TO_SKILL_ID, id);
		args.putString(ARG_ITEM_TO_SKILL_FROM, from);
		ItemToSkillFragment f = new ItemToSkillFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.item_to_skill_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_decoration_skill_list, null);
		return v;
	}

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long mId = args.getLong(ARG_ITEM_TO_SKILL_ID, -1);
		String mFrom = args.getString(ARG_ITEM_TO_SKILL_FROM);
		return new ItemToSkillTreeListCursorLoader(getActivity(), "item", mId, mFrom);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor

		ItemToSkillTreeListCursorAdapter adapter = new ItemToSkillTreeListCursorAdapter(
				getActivity(), (ItemToSkillTreeCursor) cursor);
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
		Intent i = new Intent(getActivity(), SkillTreeDetailActivity.class);
		i.putExtra(SkillTreeDetailActivity.EXTRA_SKILLTREE_ID, (long) v.getTag());
		startActivity(i);
	}

	private static class ItemToSkillTreeListCursorAdapter extends CursorAdapter {

		private ItemToSkillTreeCursor mItemToSkillTreeCursor;

		public ItemToSkillTreeListCursorAdapter(Context context,
				ItemToSkillTreeCursor cursor) {
			super(context, cursor, 0);
			mItemToSkillTreeCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_decoration_skill,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the item for the current row
			ItemToSkillTree itemToSkillTree = mItemToSkillTreeCursor
					.getItemToSkillTree();

			// Set up the text view
			LinearLayout itemLayout = (LinearLayout) view
					.findViewById(R.id.listitem);
			TextView skillTextView = (TextView) view
					.findViewById(R.id.skill);
			TextView pointTextView = (TextView) view
					.findViewById(R.id.point);

			String cellSkill = itemToSkillTree.getSkillTree().getName();
			String cellPoints = "" + itemToSkillTree.getPoints();
			
			skillTextView.setText(cellSkill);
			pointTextView.setText(cellPoints);

			itemLayout.setTag(itemToSkillTree.getSkillTree().getId());
		}
	}

}
