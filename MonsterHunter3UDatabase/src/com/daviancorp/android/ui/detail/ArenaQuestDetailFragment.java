package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daviancorp.android.data.object.ArenaQuest;
import com.daviancorp.android.loader.ArenaQuestLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ArenaQuestDetailFragment extends Fragment {
	private static final String ARG_ARENA_QUEST_ID = "ARENA_QUEST_ID";
	
	private ArenaQuest mArenaQuest;
	
	TextView mQuest;
	TextView mGoal;
	TextView mLocation;
	TextView mReward;
	TextView mParticipants;
	TextView mSRank;
	TextView mARank;
	TextView mBRank;

	public static ArenaQuestDetailFragment newInstance(long arenaQuestId) {
		Bundle args = new Bundle();
		args.putLong(ARG_ARENA_QUEST_ID, arenaQuestId);
		ArenaQuestDetailFragment f = new ArenaQuestDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Check for a Arena Quest ID as an argument, and find the arena quest
		Bundle args = getArguments();
		if (args != null) {
			long questId = args.getLong(ARG_ARENA_QUEST_ID, -1);
			if (questId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.arena_quest_detail_fragment, args, new ArenaQuestLoaderCallbacks());
			}
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_arena_quest_detail, container, false);
		
		mQuest = (TextView) view.findViewById(R.id.quest);
		mGoal = (TextView) view.findViewById(R.id.goal);
		mLocation = (TextView) view.findViewById(R.id.location);
		mReward = (TextView) view.findViewById(R.id.reward);
		mParticipants = (TextView) view.findViewById(R.id.participants);
		mSRank = (TextView) view.findViewById(R.id.s_rank);
		mARank = (TextView) view.findViewById(R.id.a_rank);
		mBRank = (TextView) view.findViewById(R.id.b_rank);

		return view;
	}
	
	private void updateUI() {
		String cellQuest = mArenaQuest.getName();
		String cellGoal = mArenaQuest.getGoal();
		String cellLocation = mArenaQuest.getLocation().getName();
		String cellReward = "" + mArenaQuest.getReward() + "z";
		String cellParticipants = "" + mArenaQuest.getNumParticipants();
		String cellSRank = "" + mArenaQuest.getTimeS();
		String cellARank = "" + mArenaQuest.getTimeA();
		String cellBRank = "" + mArenaQuest.getTimeB();
		
		mQuest.setText(cellQuest);
		mGoal.setText(cellGoal);
		mLocation.setText(cellLocation);
		mReward.setText(cellReward);
		mParticipants.setText(cellParticipants);
		mSRank.setText(cellSRank);
		mARank.setText(cellARank);
		mBRank.setText(cellBRank);
		
	}
	
	private class ArenaQuestLoaderCallbacks implements LoaderCallbacks<ArenaQuest> {
		
		@Override
		public Loader<ArenaQuest> onCreateLoader(int id, Bundle args) {
			return new ArenaQuestLoader(getActivity(), args.getLong(ARG_ARENA_QUEST_ID));
		}
		
		@Override
		public void onLoadFinished(Loader<ArenaQuest> loader, ArenaQuest arenaQuest) {
			mArenaQuest = arenaQuest;
			updateUI();
		}
		
		@Override
		public void onLoaderReset(Loader<ArenaQuest> loader) {
			// Do nothing
		}
	}
}
