package com.daviancorp.android.data.object;

public class QuestReward {

	private long id;
	private Quest quest;
	private Item item;
	private String reward_slot;
	private int percentage;
	private int stack_size;
	
	public QuestReward() {
		this.id = -1;
		this.quest = null;
		this.item = null;
		this.reward_slot = "";
		this.percentage = -1;
		this.stack_size = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Quest getQuest() {
		return quest;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getRewardSlot() {
		return reward_slot;
	}

	public void setRewardSlot(String reward_slot) {
		this.reward_slot = reward_slot;
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
