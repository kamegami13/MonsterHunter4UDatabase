package com.daviancorp.android.data.object;

/*
 * Class for Gathering
 */
public class Gathering {

	private long id;			// Gathering id
	private Item item;			// Item gathered
	private Location location;	// Location gathered
	private String area;		// Area # of location
	private String site;		// Type of gathering node; bug, mine, fish, etc.
	private String rank;		// Quest Rank found in
	
	/* Default Constructor */
	public Gathering() {
		this.id = -1;
		this.item = null;
		this.location = null;
		this.area = "";
		this.site = "";
		this.rank = "";
	}

	/* Getters and Setters */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
}
