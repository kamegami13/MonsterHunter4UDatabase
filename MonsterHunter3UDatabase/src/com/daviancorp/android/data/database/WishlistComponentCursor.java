package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.WishlistComponent;
import com.daviancorp.android.data.object.Item;

/**
 * A convenience class to wrap a cursor that returns rows from the "wishlist_component"
 * table. The {@link getWishlistComponent()} method will give you a WishlistComponent instance
 * representing the current row.
 */
public class WishlistComponentCursor extends CursorWrapper {

	public WishlistComponentCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a WishlistComponent object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public WishlistComponent getWishlistComponent() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		WishlistComponent wishlistComponent = new WishlistComponent();
		
		long id = getLong(getColumnIndex(S.COLUMN_WISHLIST_COMPONENT_ID));
		long wishlist_id = getLong(getColumnIndex(S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID));
		int quantity = getInt(getColumnIndex(S.COLUMN_WISHLIST_COMPONENT_QUANTITY));
		int notes = getInt(getColumnIndex(S.COLUMN_WISHLIST_COMPONENT_NOTES));
		
		wishlistComponent.setId(id);
		wishlistComponent.setWishlistId(wishlist_id);
		wishlistComponent.setQuantity(quantity);
		wishlistComponent.setNotes(notes);

		Item item = new Item();
		
		long itemId = getLong(getColumnIndex(S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID));
		String itemName = getString(getColumnIndex(S.COLUMN_ITEMS_NAME));
//			String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
			String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
			int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
//			int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
//			int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
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
//			item.setBuy(buy);
//			item.setSell(sell);
//			item.setDescription(description);
			item.setFileLocation(fileLocation);
//			item.setArmorDupeNameFix(armor_dupe_name_fix);
		
		wishlistComponent.setItem(item);
		
		return wishlistComponent;
	}

}