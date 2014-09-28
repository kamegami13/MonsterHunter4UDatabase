package com.daviancorp.android.data.object;

public class WishlistComponent {

	private long id;
	private long wishlist_id;
	private Item item;
	private int quantity;
	private int notes;
	
	public WishlistComponent() {
		this.id = -1;
		this.quantity = -1;
		this.item = null;
		this.quantity = -1;
		this.notes = -1;
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
	
	public int getNotes() {
		return notes;
	}
	
	public void setNotes(int notes) {
		this.notes = notes;
	}
}
