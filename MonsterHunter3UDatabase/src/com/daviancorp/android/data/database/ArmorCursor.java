package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.Armor;

/**
 * A convenience class to wrap a cursor that returns rows from the "armor"
 * table. The {@link getArmor()} method will give you a Armor instance
 * representing the current row.
 */
public class ArmorCursor extends CursorWrapper {

	public ArmorCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a Armor object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public Armor getArmor() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		Armor armor = new Armor();

		String slot = getString(getColumnIndex(S.COLUMN_ARMOR_SLOT));
		int defense = getInt(getColumnIndex(S.COLUMN_ARMOR_DEFENSE));
		int max_defense = getInt(getColumnIndex(S.COLUMN_ARMOR_MAX_DEFENSE));
		int fire_res = getInt(getColumnIndex(S.COLUMN_ARMOR_FIRE_RES));
		int thunder_res = getInt(getColumnIndex(S.COLUMN_ARMOR_THUNDER_RES));
		int dragon_res = getInt(getColumnIndex(S.COLUMN_ARMOR_DRAGON_RES));
		int water_res = getInt(getColumnIndex(S.COLUMN_ARMOR_WATER_RES));
		int ice_res = getInt(getColumnIndex(S.COLUMN_ARMOR_ICE_RES));
		String gender = getString(getColumnIndex(S.COLUMN_ARMOR_GENDER));
		String hunter_type = getString(getColumnIndex(S.COLUMN_ARMOR_HUNTER_TYPE));
		int num_slots = getInt(getColumnIndex(S.COLUMN_ARMOR_NUM_SLOTS));
		
		armor.setSlot(slot);
		armor.setDefense(defense);
		armor.setMaxDefense(max_defense);
		armor.setFireRes(fire_res);
		armor.setThunderRes(thunder_res);
		armor.setDragonRes(dragon_res);
		armor.setWaterRes(water_res);
		armor.setIceRes(ice_res);
		armor.setGender(gender);
		armor.setHunterType(hunter_type);
		armor.setNumSlots(num_slots);

		long itemId = getLong(getColumnIndex(S.COLUMN_ITEMS_ID));
		String name = getString(getColumnIndex(S.COLUMN_ITEMS_NAME));
		String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
		String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
		int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
		int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
		int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
		int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
		String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
		String fileLocation = getString(getColumnIndex(S.COLUMN_ITEMS_ICON_NAME));
		String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));
		
		armor.setId(itemId);
		armor.setName(name);
		armor.setJpnName(jpnName);
		armor.setType(type);
		armor.setRarity(rarity);
		armor.setCarryCapacity(carry_capacity);
		armor.setBuy(buy);
		armor.setSell(sell);
		armor.setDescription(description);
		armor.setFileLocation(fileLocation);
		armor.setArmorDupeNameFix(armor_dupe_name_fix);

		return armor;
	}

}