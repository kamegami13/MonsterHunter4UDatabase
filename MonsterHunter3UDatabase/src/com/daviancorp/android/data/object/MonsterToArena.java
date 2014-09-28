package com.daviancorp.android.data.object;

public class MonsterToArena {

	private long id;
	private Monster monster;
	private ArenaQuest arenaQuest;
	private String unstable;
	
	public MonsterToArena() {
		this.id = -1;
		this.monster = null;
		this.arenaQuest = null;
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

	public ArenaQuest getArenaQuest() {
		return arenaQuest;
	}

	public void setArenaQuest(ArenaQuest arenaQuest) {
		this.arenaQuest = arenaQuest;
	}

	public String getUnstable() {
		return unstable;
	}

	public void setUnstable(String unstable) {
		this.unstable = unstable;
	}
}
