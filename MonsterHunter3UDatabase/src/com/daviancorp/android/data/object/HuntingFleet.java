package com.daviancorp.android.data.object;

public class HuntingFleet {
	
	private long id;
	private String type;
	private int level;
	private String location;
	private Item item;
	private int amount;
	private int percentage;
	
	public HuntingFleet() {
		this.id = -1;
		this.type = "";
		this.level = -1;
		this.location = "";
		this.item = null;
		this.amount = -1;
		this.percentage = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
}
