package com.daviancorp.android.data.object;

public class Gathering {

	private long id;
	private Item item;
	private Location location;
	private String area;
	private String site;
	private String rank;
	
	public Gathering() {
		this.id = -1;
		this.item = null;
		this.location = null;
		this.area = "";
		this.site = "";
		this.rank = "";
	}

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
