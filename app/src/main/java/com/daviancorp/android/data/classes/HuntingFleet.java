package com.daviancorp.android.data.classes;

/*
 * Class for HuntingFleet
 */
public class HuntingFleet {
	
	private long id;			// HuntingFleet id
	private String type;		// Type of HuntingFleet
	private int level;			// Level of HuntingFleet; 1-4
	private String location;	// Location of HuntingFleet; name
	private Item item;			// Item obtained
	private int amount;			// Amount of the Item obtained
	private int percentage;		// Percentage in obtaining Item
	
	/* Default constructor */
	public HuntingFleet() {
		this.id = -1;
		this.type = "";
		this.level = -1;
		this.location = "";
		this.item = null;
		this.amount = -1;
		this.percentage = -1;
	}

	/* Getters and Setters */
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
