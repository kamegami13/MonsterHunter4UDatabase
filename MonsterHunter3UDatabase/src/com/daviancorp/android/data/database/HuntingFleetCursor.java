package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.HuntingFleet;
import com.daviancorp.android.data.object.Item;

/**
 * A convenience class to wrap a cursor that returns rows from the
 * "hunting_fleet" table. The {@link getHuntingFleet()} method will give you a
 * HuntingFleet instance representing the current row.
 */
public class HuntingFleetCursor extends CursorWrapper {

	public HuntingFleetCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a HuntingFleet object configured for the current row, or null if
	 * the current row is invalid.
	 */
	public HuntingFleet getHuntingFleet() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		HuntingFleet huntingFleet = new HuntingFleet();
		Item item = new Item();

		long huntingFleetID = getLong(getColumnIndex(S.COLUMN_HUNTING_FLEET_ID));
		String huntingFleetType = getString(getColumnIndex("h"
				+ S.COLUMN_HUNTING_FLEET_TYPE));
		int level = getInt(getColumnIndex(S.COLUMN_HUNTING_FLEET_LEVEL));
		String location = getString(getColumnIndex(S.COLUMN_HUNTING_FLEET_LOCATION));
		long itemId = getLong(getColumnIndex(S.COLUMN_HUNTING_FLEET_ITEM_ID));
		item.setId(itemId);
		int amount = getInt(getColumnIndex(S.COLUMN_HUNTING_FLEET_AMOUNT));
		int percentage = getInt(getColumnIndex(S.COLUMN_HUNTING_FLEET_PERCENTAGE));

		huntingFleet.setId(huntingFleetID);
		huntingFleet.setType(huntingFleetType);
		huntingFleet.setLevel(level);
		huntingFleet.setLocation(location);
		huntingFleet.setAmount(amount);
		huntingFleet.setPercentage(percentage);

		String itemName = getString(getColumnIndex(S.COLUMN_ITEMS_NAME));
		String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
		String itemType = getString(getColumnIndex("i" + S.COLUMN_ITEMS_TYPE));
		int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
		int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
		int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
		int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
		String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
		String fileLocation = getString(getColumnIndex(S.COLUMN_ITEMS_ICON_NAME));
		String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));

		item.setName(itemName);
		item.setJpnName(jpnName);
		item.setType(itemType);
		item.setRarity(rarity);
		item.setCarryCapacity(carry_capacity);
		item.setBuy(buy);
		item.setSell(sell);
		item.setDescription(description);
		item.setFileLocation(fileLocation);
		item.setArmorDupeNameFix(armor_dupe_name_fix);

		huntingFleet.setItem(item);

		return huntingFleet;
	}

}