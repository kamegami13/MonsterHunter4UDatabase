package com.daviancorp.android.data.object;

public class WishlistData {

	private long id;
	private long wishlist_id;
	private Item item;
	private int quantity;
	private int satisfied;
	private String path;
	
	public WishlistData() {
		this.id = -1;
		this.wishlist_id = -1;
		this.item = null;
		this.quantity = -1;
		this.satisfied = -1;
		this.path = "";
	}
	
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
