package com.daviancorp.android.data.object;

public class SkillTree {

	private long id;
	private String name;
	private String jpn_name;
	
	public SkillTree() {
		this.id = -1;
		this.name = "";
		this.jpn_name = "";
	}
	
	public SkillTree(long id, String name, String jpn_name) {
		this.id = id;
		this.name = name;
		this.jpn_name = jpn_name;
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
	
}
