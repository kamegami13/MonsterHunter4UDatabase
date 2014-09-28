package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.daviancorp.android.data.database.DataManager;

/*
 * TODO: Need to look over loadCursor() again
 */
public class MogaWoodsRewardListCursorLoader extends SQLiteCursorLoader {
	private String from;	// "item" or "monster"
	private long id; 		// Item or Monster id
	private String time; 	// (For Monsters only) "Day", "Night", "Both" (Default)

	public MogaWoodsRewardListCursorLoader(Context context, String from, long id, String time) {
		super(context);
		this.from = from;
		this.id = id;
		this.time = time;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals("item")) {
			return DataManager.get(getContext()).queryMogaWoodsRewardItem(id);
		}
		else if(from.equals("monster") && time != null && !time.equals("")) {
			return DataManager.get(getContext()).queryMogaWoodsRewardMonsterTime(id, time);
		}
		else if(from.equals("monster")) {
			return DataManager.get(getContext()).queryMogaWoodsRewardMonster(id);
		}
		else {
			Log.d("heyo", "MogaWoodsRewardCursorLoader: bad arg!!! + (" + from + ")");
			return null;
		}
	}
}
