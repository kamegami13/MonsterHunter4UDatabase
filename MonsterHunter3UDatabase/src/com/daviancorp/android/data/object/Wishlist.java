package com.daviancorp.android.data.object;

public class Wishlist {

	private long id;
	private String name;
	
	public Wishlist() {
		this.id = -1;
		this.name = "";
	}
	
	public Wishlist(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
