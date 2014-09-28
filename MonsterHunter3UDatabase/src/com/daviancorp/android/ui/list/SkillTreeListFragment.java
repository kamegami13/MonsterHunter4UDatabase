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

import com.daviancorp.android.data.database.SkillTreeCursor;
import com.daviancorp.android.data.object.SkillTree;
import com.daviancorp.android.loader.SkillTreeListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.SkillTreeDetailActivity;

public class SkillTreeListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.skill_tree_list_fragment, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		return new SkillTreeListCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		SkillTreeListCursorAdapter adapter = new SkillTreeListCursorAdapter(
				getActivity(), (SkillTreeCursor) cursor);
		setListAdapter(adapter);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// The id argument will be the Skill ID; CursorAdapter gives us this for free
		Intent i = new Intent(getActivity(), SkillTreeDetailActivity.class);
		i.putExtra(SkillTreeDetailActivity.EXTRA_SKILLTREE_ID, id);
		startActivity(i);
	}

	private static class SkillTreeListCursorAdapter extends CursorAdapter {

		private SkillTreeCursor mSkillTreeCursor;

		public SkillTreeListCursorAdapter(Context context,
				SkillTreeCursor cursor) {
			super(context, cursor, 0);
			mSkillTreeCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_skilltree_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			SkillTree skilltree = mSkillTreeCursor.getSkillTree();

			// Set up the text view
			TextView skilltreeNameTextView = (TextView) view.findViewById(R.id.item);
			String cellText = skilltree.getName();
			skilltreeNameTextView.setText(cellText);
			
			
		}
	}

}
