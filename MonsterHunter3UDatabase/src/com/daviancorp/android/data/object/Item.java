package com.daviancorp.android.data.object;

public class Item {

	private long id;
	private String name;
	private String jpn_name;
	private String type;
	private int rarity;
	private int carry_capacity;
	private int buy;
	private int sell;
	private String description;
	private String file_location;
	private String armor_dupe_name_fix;
	
	public Item() {
		this.id = -1;
		this.name = "";
		this.jpn_name = "";
		this.type = "";
		this.rarity = -1;
		this.carry_capacity = -1;
		this.buy = -1;
		this.sell = -1;
		this.description = "";
		this.file_location = "";
		this.armor_dupe_name_fix = "";
	}

	public Item(long id, String name, String jpn_name, String type, int rarity,
			int carry_capacity, int buy, int sell, String description,
			String file_location, String armor_dupe_name_fix) {
		this.id = id;
		this.name = name;
		this.jpn_name = jpn_name;
		this.type = type;
		this.rarity = rarity;
		this.carry_capacity = carry_capacity;
		this.buy = buy;
		this.sell = sell;
		this.description = description;
		this.file_location = file_location;
		this.armor_dupe_name_fix = armor_dupe_name_fix;
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


	public String getJpnName() {
		return jpn_name;
	}


	public void setJpnName(String jpn_name) {
		this.jpn_name = jpn_name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getRarity() {
		return rarity;
	}


	public void setRarity(int rarity) {
		this.rarity = rarity;
	}


	public int getCarryCapacity() {
		return carry_capacity;
	}


	public void setCarryCapacity(int carry_capacity) {
		this.carry_capacity = carry_capacity;
	}


	public int getBuy() {
		return buy;
	}


	public void setBuy(int buy) {
		this.buy = buy;
	}


	public int getSell() {
		return sell;
	}


	public void setSell(int sell) {
		this.sell = sell;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getFileLocation() {
		return file_location;
	}


	public void setFileLocation(String file_location) {
		this.file_location = file_location;
	}


	public String getArmorDupeNameFix() {
		return armor_dupe_name_fix;
	}


	public void setArmorDupeNameFix(String armor_dupe_name_fix) {
		this.armor_dupe_name_fix = armor_dupe_name_fix;
	}


}

