package com.daviancorp.android.data.object;

/*
 * Class for HuntingReward
 */
public class HuntingReward {

	private long id;			// HuntingReward id
	private Item item;			// Item id
	private String condition;	// Condition to obtain Item
	private Monster monster;	// Monster that drops Item
	private String rank;		// Quest rank
	private int stack_size;		// Amount of Item dropped
	private int percentage;		// Percentage of obtaining Item
	
	/* Default Constructor */
	public HuntingReward() {
		this.id = -1;
		this.item = null;
		this.condition = "";
		this.monster = null;
		this.rank = "";
		this.stack_size = -1;
		this.percentage = -1;
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getStackSize() {
		return stack_size;
	}

	public void setStackSize(int stack_size) {
		this.stack_size = stack_size;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
}
