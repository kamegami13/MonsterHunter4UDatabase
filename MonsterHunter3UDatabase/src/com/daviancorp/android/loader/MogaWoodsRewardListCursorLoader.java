package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

/*
 * TODO: Need to look over loadCursor() again
 */
public class MogaWoodsRewardListCursorLoader extends SQLiteCursorLoader {
	public static String FROM_ITEM = "item";
	public static String FROM_MONSTER = "monster";
	
	public static String TIME_DAY = "Day";
	public static String TIME_NIGHT = "Night";
	public static String TIME_BOTH = "Both";
	
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
		if (from.equals(FROM_ITEM)) {
			// Query the list of Moga Woods reward based on item
			return DataManager.get(getContext()).queryMogaWoodsRewardItem(id);
		}
		else if(from.equals(FROM_MONSTER) && time != null && !time.equals("")) {
			// Query the list of Moga Woods reward based on monster and time
			return DataManager.get(getContext()).queryMogaWoodsRewardMonsterTime(id, time);
		}
		else if(from.equals(FROM_MONSTER)) {
			// Query the list of Moga Woods reward based on monster
			return DataManager.get(getContext()).queryMogaWoodsRewardMonster(id);
		}
		else {
			return null;
		}
	}
}
