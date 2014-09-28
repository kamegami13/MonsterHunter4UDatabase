package com.daviancorp.android.ui.list;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Quest;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.QuestDetailActivity;

/**
 * Pieced together from: Android samples:
 * com.example.android.apis.view.ExpandableList1
 * http://androidword.blogspot.com/2012/01/how-to-use-expandablelistview.html
 * http://stackoverflow.com/questions/6938560/android-fragments-setcontentview-
 * alternative
 * http://stackoverflow.com/questions/6495898/findviewbyid-in-fragment-android
 */
public class QuestExpandableListFragment extends Fragment {
	private String mHub;
	private static final String ARG_HUB = "QUEST_HUB";
	private ArrayList<Quest> quests;
	private String[] village = { "1 ", "2 ", "3 ", "4 ", "5 ", "6 ", "7 ",
			"8 ", "9 " };

	private String[] port_dlc = { "1 ", "2 ", "3 ", "4 ", "5 ", "6 ", "7 ",
			"8 " };

	private ArrayList<ArrayList<Quest>> children;

	public static QuestExpandableListFragment newInstance(String hub) {
		Bundle args = new Bundle();
		args.putString(ARG_HUB, hub);
		QuestExpandableListFragment f = new QuestExpandableListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHub = null;
		Bundle args = getArguments();
		if (args != null) {
			mHub = args.getString(ARG_HUB);
		}
		populateList();

	}

	private void populateList() {
		children = new ArrayList<ArrayList<Quest>>();
		quests = DataManager.get(getActivity()).queryQuestArrayHub(mHub);
		ArrayList<Quest> g1 = new ArrayList<Quest>();
		ArrayList<Quest> g2 = new ArrayList<Quest>();
		ArrayList<Quest> g3 = new ArrayList<Quest>();
		ArrayList<Quest> g4 = new ArrayList<Quest>();
		ArrayList<Quest> g5 = new ArrayList<Quest>();
		ArrayList<Quest> g6 = new ArrayList<Quest>();
		ArrayList<Quest> g7 = new ArrayList<Quest>();
		ArrayList<Quest> g8 = new ArrayList<Quest>();
		ArrayList<Quest> g9 = new ArrayList<Quest>();
		for (int i = 0; i < quests.size(); i++) {
			switch (quests.get(i).getStars()) {

			case "1":
				g1.add(quests.get(i));
				break;
			case "2":
				g2.add(quests.get(i));
				break;
			case "3":
				g3.add(quests.get(i));
				break;
			case "4":
				g4.add(quests.get(i));
				break;
			case "5":
				g5.add(quests.get(i));
				break;
			case "6":
				g6.add(quests.get(i));
				break;
			case "7":
				g7.add(quests.get(i));
				break;
			case "8":
				g8.add(quests.get(i));
				break;
			case "9":
				g9.add(quests.get(i));
				break;
			default:
				break;
			}
		}
		children.add(g1);
		children.add(g2);
		children.add(g3);
		children.add(g4);
		children.add(g5);
		children.add(g6);
		children.add(g7);
		children.add(g8);
		children.add(g9);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_quest_expandablelist, null);
		ExpandableListView elv = (ExpandableListView) v
				.findViewById(R.id.expandableListView);
		if (mHub.equals("Village")) {
			elv.setAdapter(new QuestListAdapter(village));
		} else {
			elv.setAdapter(new QuestListAdapter(port_dlc));
		}

		elv.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long id) {

				Intent i = new Intent(getActivity(), QuestDetailActivity.class);
				i.putExtra(QuestDetailActivity.EXTRA_QUEST_ID,
						(long) arg1.getTag());
				startActivity(i);

				return false;
			}
		});

		return v;

	}

	public class QuestListAdapter extends BaseExpandableListAdapter {

		private String[] quests;

		public QuestListAdapter(String[] quests) {
			super();
			this.quests = quests;

		}

		@Override
		public int getGroupCount() {
			return quests.length;
		}

		@Override
		public int getChildrenCount(int i) {
			return children.get(i).size();
		}

		@Override
		public Object getGroup(int i) {
			return quests[i];
		}

		@Override
		public Object getChild(int i, int i1) {
			return children.get(i).get(i1);
		}

		@Override
		public long getGroupId(int i) {
			return i;
		}

		@Override
		public long getChildId(int i, int i1) {
			return i1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getGroupView(int i, boolean b, View view,
				ViewGroup viewGroup) {
			// TextView textView = new TextView(
			// QuestExpandableListFragment.this.getActivity());
			// textView.setText(getGroup(i).toString());
			// return textView;

			View v = view;
			Context context = viewGroup.getContext();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(
					R.layout.fragment_quest_expandablelist_group_item,
					viewGroup, false);

			TextView armorGroupTextView = (TextView) v.findViewById(R.id.name);

			armorGroupTextView.setText(getGroup(i).toString());

			return v;
		}

		@Override
		public View getChildView(int i, int i1, boolean b, View view,
				ViewGroup viewGroup) {
			// TextView textView = new TextView(
			// QuestExpandableListFragment.this.getActivity());

			View v = view;
			Context context = viewGroup.getContext();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(
					R.layout.fragment_quest_expandablelist_child_item,
					viewGroup, false);

			TextView questChildTextView = (TextView) v.findViewById(R.id.name);
			LinearLayout root = (LinearLayout) v.findViewById(R.id.root);

			questChildTextView.setText(getChild(i, i1).toString());

			root.setTag(((Quest) getChild(i, i1)).getId());
			return v;
		}

		@Override
		public boolean isChildSelectable(int i, int i1) {
			return true;
		}

	}

}