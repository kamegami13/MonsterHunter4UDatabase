package com.daviancorp.android.data.object;

/*
 * Note: No constructor with fields
 */
public class Combining {

	private long id;
	private Item created_item;
	private Item item_1;
	private Item item_2;
	private int amount_made_min;
	private int amount_made_max;
	private int percentage;
	
	public Combining() {
		this.id = -1;
		this.created_item = null;
		this.item_1 = null;
		this.item_2 = null;
		this.amount_made_min = -1;
		this.amount_made_max = -1;
		this.percentage = -1;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Item getCreatedItem() {
		return created_item;
	}
	
	public void setCreatedItem(Item created_item) {
		this.created_item = created_item;
	}
	
	public Item getItem1() {
		return item_1;
	}
	
	public void setItem1(Item item_1) {
		this.item_1 = item_1;
	}
	
	public Item getItem2() {
		return item_2;
	}
	
	public void setItem2(Item item_2) {
		this.item_2 = item_2;
	}
	
	public int getAmountMadeMin() {
		return amount_made_min;
	}
	
	public void setAmountMadeMin(int amount_made_min) {
		this.amount_made_min = amount_made_min;
	}
	
	public int getAmountMadeMax() {
		return amount_made_max;
	}
	
	public void setAmountMadeMax(int amount_made_max) {
		this.amount_made_max = amount_made_max;
	}
	
	public int getPercentage() {
		return percentage;
	}
	
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
}
