package com.daviancorp.android.ui.detail;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daviancorp.android.data.database.SkillCursor;
import com.daviancorp.android.data.object.Skill;
import com.daviancorp.android.loader.SkillListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class SkillTreeDetailFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String ARG_SKILL = "SKILLTREE_SKILL";

	public static SkillTreeDetailFragment newInstance(Long skill) {
		Bundle args = new Bundle();
		args.putLong(ARG_SKILL, skill);
		SkillTreeDetailFragment f = new SkillTreeDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.skill_tree_detail_fragment, getArguments(), this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_skill_detail_list, null);
		return v;
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		Long mSkill = null;
		if (args != null) {
			mSkill = args.getLong(ARG_SKILL);
		}
		return new SkillListCursorLoader(getActivity(), mSkill);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		SkillListCursorAdapter adapter = new SkillListCursorAdapter(
				getActivity(), (SkillCursor) cursor);
		setListAdapter(adapter);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Stop using the cursor (via the adapter)
		setListAdapter(null);
	}

	private static class SkillListCursorAdapter extends CursorAdapter {

		private SkillCursor mSkillCursor;

		public SkillListCursorAdapter(Context context, SkillCursor cursor) {
			super(context, cursor, 0);
			mSkillCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_skill_detail_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the skill for the current row
			Skill skill = mSkillCursor.getSkill();

			// Set up the text view
			TextView skillNameTextView = (TextView) view.findViewById(R.id.skill);
			TextView skillPtTextView = (TextView) view.findViewById(R.id.pts);
			TextView skillDescTextView = (TextView) view.findViewById(R.id.description);
			
			String nameText = skill.getName();
			String ptText = "" + skill.getRequiredPoints();
			String descText = skill.getDescription();
			
			skillNameTextView.setText(nameText);
			skillPtTextView.setText(ptText);
			skillDescTextView.setText(descText);
		}
		
		@Override
		public boolean isEnabled(int position) {
			return false;
		}
	}

}
