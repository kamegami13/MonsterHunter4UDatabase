package com.daviancorp.android.data.object;

public class MonsterToQuest {

	private long id;
	private Monster monster;
	private Quest quest;
	private String unstable;
	
	public MonsterToQuest() {
		this.id = -1;
		this.monster = null;
		this.quest = null;
		this.unstable = null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public Quest getQuest() {
		return quest;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public String getUnstable() {
		return unstable;
	}

	public void setUnstable(String unstable) {
		this.unstable = unstable;
	}
}
