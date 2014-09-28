package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.WishlistData;
import com.daviancorp.android.data.object.Item;

/**
 * A convenience class to wrap a cursor that returns rows from the "wishlist_data"
 * table. The {@link getWishlistData()} method will give you a WishlistData instance
 * representing the current row.
 */
public class WishlistDataCursor extends CursorWrapper {

	public WishlistDataCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a WishlistData object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public WishlistData getWishlistData() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		WishlistData wishlistData = new WishlistData();
		
		long id = getLong(getColumnIndex(S.COLUMN_WISHLIST_DATA_ID));
		long wishlist_id = getLong(getColumnIndex(S.COLUMN_WISHLIST_DATA_WISHLIST_ID));
		int quantity = getInt(getColumnIndex(S.COLUMN_WISHLIST_DATA_QUANTITY));
		int satisfied = getInt(getColumnIndex(S.COLUMN_WISHLIST_DATA_SATISFIED));
		String path = getString(getColumnIndex(S.COLUMN_WISHLIST_DATA_PATH));
		
		wishlistData.setId(id);
		wishlistData.setWishlistId(wishlist_id);
		wishlistData.setQuantity(quantity);
		wishlistData.setSatisfied(satisfied);
		wishlistData.setPath(path);

		Item item = new Item();
		
		long itemId = getLong(getColumnIndex(S.COLUMN_WISHLIST_DATA_ITEM_ID));
		String itemName = getString(getColumnIndex(S.COLUMN_ITEMS_NAME));
//			String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
			String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
			int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
//			int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
		int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
//			int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
//			String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
			String fileLocation = getString(getColumnIndex(S.COLUMN_ITEMS_ICON_NAME));
//			String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));

		item.setId(itemId);
		item.setName(itemName);
//			item.setJpnName(jpnName);
			item.setType(type);
			item.setRarity(rarity);
//			item.setCarryCapacity(carry_capacity);
		item.setBuy(buy);
//			item.setSell(sell);
//			item.setDescription(description);
			item.setFileLocation(fileLocation);
//			item.setArmorDupeNameFix(armor_dupe_name_fix);
		
		wishlistData.setItem(item);
		
		return wishlistData;
	}

}