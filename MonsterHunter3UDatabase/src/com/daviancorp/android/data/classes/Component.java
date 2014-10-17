package com.daviancorp.android.data.classes;

/*
 * Class for Component
 *
 * This is used for forging armor, weapons, and decorations
 */
public class Component {

	private long id;			// Component id
	private Item created;		// Created Item
	private Item component;		// Component Item
	private int quantity;		// Amount needed for the component Item
	private String type;		// Creation method
	
	/* Default Constructor */
	public Component() {
		this.id = -1;
		this.created = null;
		this.component = null;
		this.quantity = -1;
		this.type = "";
	}

	/* Getters and Setters */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Item getCreated() {
		return created;
	}

	public void setCreated(Item created) {
		this.created = created;
	}

	public Item getComponent() {
		return component;
	}

	public void setComponent(Item component) {
		this.component = component;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
