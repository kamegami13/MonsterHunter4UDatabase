package com.daviancorp.android.data.object;

/*
 * Class for MonsterToQuest
 */
public class MonsterToQuest {

	private long id;			// id
	private Monster monster;	// Monster
	private Quest quest;		// Quest
	private String unstable;	// Unstable or not
	
	/* Default Constructors */
	public MonsterToQuest() {
		this.id = -1;
		this.monster = null;
		this.quest = null;
		this.unstable = null;
	}

	/* Getters and Setters */
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
