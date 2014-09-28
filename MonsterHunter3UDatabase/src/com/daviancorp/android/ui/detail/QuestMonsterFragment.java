package com.daviancorp.android.ui.detail;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.daviancorp.android.data.database.MonsterToQuestCursor;
import com.daviancorp.android.data.object.MonsterToQuest;
import com.daviancorp.android.loader.MonsterToQuestListCursorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class QuestMonsterFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String ARG_QUEST_ID = "QUEST_ID";

	public static QuestMonsterFragment newInstance(long questId) {
		Bundle args = new Bundle();
		args.putLong(ARG_QUEST_ID, questId);
		QuestMonsterFragment f = new QuestMonsterFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the loader to load the list of runs
		getLoaderManager().initLoader(R.id.quest_monster_fragment, getArguments(), this);
	}

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// You only ever load the runs, so assume this is the case
		long questId = args.getLong(ARG_QUEST_ID, -1);

		return new MonsterToQuestListCursorLoader(getActivity(), "quest",
				questId);
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
		Intent i = new Intent(getActivity(), MonsterDetailActivity.class);
		i.putExtra(MonsterDetailActivity.EXTRA_MONSTER_ID, (long) v.getTag());
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
			return inflater.inflate(R.layout.fragment_quest_monstertoquest,
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
			ImageView monsterImageView = (ImageView) view
					.findViewById(R.id.detail_monster_image);
			TextView monsterTextView = (TextView) view
					.findViewById(R.id.detail_monster_label);
			TextView unstableTextView = (TextView) view
					.findViewById(R.id.detail_monster_unstable);
			
			String cellMonsterText = monsterToQuest.getMonster().getName();
			String cellTraitText = monsterToQuest.getMonster().getTrait(); 
			String cellUnstableText = monsterToQuest.getUnstable();
			
			if (!cellTraitText.equals("")) {
				cellMonsterText = cellMonsterText + " (" + cellTraitText + ")";
			}
			if (cellUnstableText.equals("no")) {
				cellUnstableText = "";
			}
			else {
				cellUnstableText = "Unstable";
			}
			
			monsterTextView.setText(cellMonsterText);
			unstableTextView.setText(cellUnstableText);

			Drawable i = null;
			String cellImage = "icons_monster/"
					+ monsterToQuest.getMonster().getFileLocation();
			try {
				i = Drawable.createFromStream(
						context.getAssets().open(cellImage), null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			monsterImageView.setImageDrawable(i);

			itemLayout.setTag(monsterToQuest.getMonster().getId());
		}
	}

}
