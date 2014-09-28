package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.daviancorp.android.data.object.Decoration;

/**
 * A convenience class to wrap a cursor that returns rows from the "decorations"
 * table. The {@link getDecoration()} method will give you a Decoration instance
 * representing the current row.
 */
public class DecorationCursor extends CursorWrapper {

	public DecorationCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a Decoration object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public Decoration getDecoration() {
		if (isBeforeFirst() || isAfterLast())
			return null;
		
		Decoration decoration = new Decoration();

		long decorationId = getLong(getColumnIndex("_id"));
		String name = getString(getColumnIndex("item_name"));
		String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
		String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
		int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
		int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
		int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
		int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
		String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
		String fileLocation = getString(getColumnIndex(S.COLUMN_ITEMS_ICON_NAME));
		String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));
		
		int num_slots = getInt(getColumnIndex(S.COLUMN_DECORATIONS_NUM_SLOTS));
		
		long skill_1_id = getLong(getColumnIndex("skill_1_id"));
		String skill_1_name = getString(getColumnIndex("skill_1_name"));
		int skill_1_point = getInt(getColumnIndex("skill_1_point_value"));
		
		long skill_2_id = getLong(getColumnIndex("skill_2_id"));
		String skill_2_name = getString(getColumnIndex("skill_2_name"));
		int skill_2_point = getInt(getColumnIndex("skill_2_point_value"));

		decoration.setId(decorationId);
		decoration.setName(name);
		decoration.setJpnName(jpnName);
		decoration.setType(type);
		decoration.setRarity(rarity);
		decoration.setCarryCapacity(carry_capacity);
		decoration.setBuy(buy);
		decoration.setSell(sell);
		decoration.setDescription(description);
		decoration.setFileLocation(fileLocation);
		decoration.setArmorDupeNameFix(armor_dupe_name_fix);		
		
		decoration.setNumSlots(num_slots);
		
		decoration.setSkill1Id(skill_1_id);
		decoration.setSkill1Name(skill_1_name);
		decoration.setSkill1Point(skill_1_point);
		
		decoration.setSkill2Id(skill_2_id);
		decoration.setSkill2Name(skill_2_name);
		decoration.setSkill2Point(skill_2_point);

		return decoration;
	}
	
}