package com.daviancorp.android.data.object;

public class ArenaReward {

	private long id;
	private ArenaQuest arenaQuest;
	private Item item;
	private int percentage;
	private int stack_size;
	
	public ArenaReward() {
		this.id = -1;
		this.arenaQuest = null;
		this.item = null;
		this.percentage = -1;
		this.stack_size = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArenaQuest getArenaQuest() {
		return arenaQuest;
	}

	public void setArenaQuest(ArenaQuest arenaQuest) {
		this.arenaQuest = arenaQuest;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public int getStackSize() {
		return stack_size;
	}

	public void setStackSize(int stack_size) {
		this.stack_size = stack_size;
	}
	
}
