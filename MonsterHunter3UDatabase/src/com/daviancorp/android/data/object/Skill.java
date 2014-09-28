package com.daviancorp.android.data.object;

public class Skill {

	private long id;
//	private SkillTree skillTree;
	private int required_points;
	private String name;
	private String jpn_name;
	private String description;
	
	public Skill() {
		this.id = -1;
		this.required_points = -1;
		this.name = "";
		this.jpn_name = "";
		this.description = "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRequiredPoints() {
		return required_points;
	}

	public void setRequiredPoints(int required_points) {
		this.required_points = required_points;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
