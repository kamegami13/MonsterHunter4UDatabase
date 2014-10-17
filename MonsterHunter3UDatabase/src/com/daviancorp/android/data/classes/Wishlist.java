package com.daviancorp.android.data.classes;

/*
 * Class for Wishlist
 */
public class Wishlist {

	private long id;			// Wishlist id
	private String name;		// Wishlist name
	
	/* Default Constructor */
	public Wishlist() {
		this.id = -1;
		this.name = "";
	}
	
	public Wishlist(long id, String name) {
		this.id = id;
		this.name = name;
	}

	/* Getters and Setters */
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
