package com.daviancorp.android.data.classes;

/*
 * Class for Item
 */
public class  Item {

	private long id;						// Item id
	private String name;					// Item name
	private String jpn_name;				// Japanese name; unused at the moment
	private String type;					// Item type
    private String sub_type;			    // Item sub type
	private int rarity;						// Rarity; 1-10
	private int carry_capacity;				// Carry capacity in backpack
	private int buy;						// Buy amount
	private int sell;						// Sell amount
	private String description;				// Item description
	private String file_location;			// File location for image
	private String armor_dupe_name_fix;		// unused at the moment?
	
	/* Default Constructor */
	public Item() {
		this.id = -1;
		this.name = "";
		this.jpn_name = "";
		this.type = "";
        this.sub_type = "";
		this.rarity = -1;
		this.carry_capacity = -1;
		this.buy = -1;
		this.sell = -1;
		this.description = "";
		this.file_location = "";
		this.armor_dupe_name_fix = "";
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


	public String getJpnName() {
		return jpn_name;
	}


	public void setJpnName(String jpn_name) {
		this.jpn_name = jpn_name;
	}


	public String getType() {
		return type;
	}


	public void setSubType(String sub_type) {
		this.sub_type = sub_type;
	}


    public String getSubType() {
        return sub_type;
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

