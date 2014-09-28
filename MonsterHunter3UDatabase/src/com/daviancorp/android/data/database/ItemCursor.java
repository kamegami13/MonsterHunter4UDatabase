package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.Item;

/**
 * A convenience class to wrap a cursor that returns rows from the "items"
 * table. The {@link getItem()} method will give you an Item instance
 * representing the current row.
 */
public class ItemCursor extends CursorWrapper {

	public ItemCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns an Item object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public Item getItem() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		Item item = new Item();

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

		item.setId(itemId);
		item.setName(name);
		item.setJpnName(jpnName);
		item.setType(type);
		item.setRarity(rarity);
		item.setCarryCapacity(carry_capacity);
		item.setBuy(buy);
		item.setSell(sell);
		item.setDescription(description);
		item.setFileLocation(fileLocation);
		item.setArmorDupeNameFix(armor_dupe_name_fix);

		return item;
	}
}
