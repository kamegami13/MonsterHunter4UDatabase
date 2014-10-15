package com.daviancorp.android.data.object;

/*
 * Class for WishlistData
 *
 * Holds the items wanted
 */
public class WishlistData {

	private long id;			// id
	private long wishlist_id;	// Wishlist
	private Item item;			// Item
	private int quantity;		// Quantity
	private int satisfied;		// Can make Item yet or no
	private String path;		// Creation path of Item
	
	/* Default Constructor */
	public WishlistData() {
		this.id = -1;
		this.wishlist_id = -1;
		this.item = null;
		this.quantity = -1;
		this.satisfied = -1;
		this.path = "";
	}
	
	/* Getters and Setters */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getWishlistId() {
		return wishlist_id;
	}

	public void setWishlistId(long wishlist_id) {
		this.wishlist_id = wishlist_id;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getSatisfied() {
		return satisfied;
	}
	
	public void setSatisfied(int satisfied) {
		this.satisfied = satisfied;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
