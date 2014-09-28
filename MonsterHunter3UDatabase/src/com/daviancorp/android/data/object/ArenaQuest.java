package com.daviancorp.android.data.object;

public class ArenaQuest {
	
	private long id;
	private String name;
	private String goal;
	private Location location;
	private int reward;
	private int num_participants;
	private String time_s;
	private String time_a;
	private String time_b;
	
	public ArenaQuest() {
		this.id = -1;
		this.name = "";
		this.goal = "";
		this.location = null;
		this.reward = -1;
		this.num_participants = -1;
		this.time_s = "";
		this.time_a = "";
		this.time_b = "";
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

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getNumParticipants() {
		return num_participants;
	}

	public void setNumParticipants(int num_participants) {
		this.num_participants = num_participants;
	}

	public String getTimeS() {
		return time_s;
	}

	public void setTimeS(String time_s) {
		this.time_s = time_s;
	}

	public String getTimeA() {
		return time_a;
	}

	public void setTimeA(String time_a) {
		this.time_a = time_a;
	}

	public String getTimeB() {
		return time_b;
	}

	public void setTimeB(String time_b) {
		this.time_b = time_b;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
}
