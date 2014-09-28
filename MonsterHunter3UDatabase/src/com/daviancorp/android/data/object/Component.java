package com.daviancorp.android.data.object;

public class Component {

	private long id;
	private Item created;
	private Item component;
	private int quantity;
	private String type;
	
	public Component() {
		this.id = -1;
		this.created = null;
		this.component = null;
		this.quantity = -1;
		this.type = "";
	}

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
