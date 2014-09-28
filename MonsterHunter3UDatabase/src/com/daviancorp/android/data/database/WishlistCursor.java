package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.Wishlist;

/**
 * A convenience class to wrap a cursor that returns rows from the "wishlist"
 * table. The {@link getWishlist()} method will give you a Wishlist instance
 * representing the current row.
 */
public class WishlistCursor extends CursorWrapper {

	public WishlistCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a Wishlist object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public Wishlist getWishlist() {
		if (isBeforeFirst() || isAfterLast())
			return null;
		
		Wishlist wishlist = new Wishlist();

		long wishlistId = getLong(getColumnIndex(S.COLUMN_WISHLIST_ID));
		String name = getString(getColumnIndex(S.COLUMN_WISHLIST_NAME));
		
		wishlist.setId(wishlistId);
		wishlist.setName(name);

		return wishlist;
	}
}