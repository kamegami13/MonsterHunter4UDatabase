package com.daviancorp.android.ui.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.object.Quest;
import com.daviancorp.android.loader.QuestLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class QuestDetailFragment extends Fragment {
	private static final String ARG_QUEST_ID = "QUEST_ID";
	
	private Quest mQuest;
	
	TextView questtv1;
	TextView questtv2;
	TextView questtv3;
	TextView questtv4;
	TextView questtv5;
	TextView questtv6;

	public static QuestDetailFragment newInstance(long questId) {
		Bundle args = new Bundle();
		args.putLong(ARG_QUEST_ID, questId);
		QuestDetailFragment f = new QuestDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Check for a Quest ID as an argument, and find the monster
		Bundle args = getArguments();
		if (args != null) {
			long questId = args.getLong(ARG_QUEST_ID, -1);
			if (questId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.quest_detail_fragment, args, new QuestLoaderCallbacks());
			}
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_quest_detail, container, false);
		
		questtv1 = (TextView) view.findViewById(R.id.level);
		questtv2 = (TextView) view.findViewById(R.id.goal);
		questtv3 = (TextView) view.findViewById(R.id.hrp);
		questtv4 = (TextView) view.findViewById(R.id.reward);
		questtv5 = (TextView) view.findViewById(R.id.fee);
		questtv6 = (TextView) view.findViewById(R.id.quest);

		return view;
	}
	
	private void updateUI() {
		String cellQuest = mQuest.getName();
		String cellLevels = mQuest.getHub() + " " + mQuest.getStars();
		String cellGoal = mQuest.getGoal();
		String cellHrp = "" + mQuest.getHrp();
		String cellReward = "" + mQuest.getReward() + "z";
		String cellFee = "" + mQuest.getFee() + "z";
		
		questtv1.setText(cellLevels);
		questtv2.setText(cellGoal);
		questtv3.setText(cellHrp);
		questtv4.setText(cellReward);
		questtv5.setText(cellFee);
		questtv6.setText(cellQuest);
		
	}
	
	private class QuestLoaderCallbacks implements LoaderCallbacks<Quest> {
		
		@Override
		public Loader<Quest> onCreateLoader(int id, Bundle args) {
			return new QuestLoader(getActivity(), args.getLong(ARG_QUEST_ID));
		}
		
		@Override
		public void onLoadFinished(Loader<Quest> loader, Quest run) {
			mQuest = run;
			updateUI();
		}
		
		@Override
		public void onLoaderReset(Loader<Quest> loader) {
			// Do nothing
		}
	}
}
