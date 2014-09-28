package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.daviancorp.android.data.database.DataManager;

public class GatheringListCursorLoader extends SQLiteCursorLoader {
	private String from;	// "item" or "location"
	private long id; 		// Item or Location id
	private String rank; 	// (For Locations only) "LR", "HR", "G", or null

	public GatheringListCursorLoader(Context context, String from, long id, String rank) {
		super(context);
		this.from = from;
		this.id = id;
		this.rank = rank;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals("item")) {
			return DataManager.get(getContext()).queryGatheringItem(id);
		}
		else if(from.equals("location") && rank != null && !rank.equals("")) {
			return DataManager.get(getContext()).queryGatheringLocationRank(id, rank);
		}
		else if(from.equals("location")) {
			return DataManager.get(getContext()).queryGatheringLocation(id);
		}
		else {
			Log.d("heyo", "GatheringCursorLoader: bad arg!!! + (" + from + ")");
			return null;
		}
	}
}
