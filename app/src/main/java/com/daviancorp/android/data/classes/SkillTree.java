package com.daviancorp.android.data.classes;

/*
 * Class for SkillTree
 */
public class SkillTree {

	private long id;			// Id
	private String name;		// SkillTree name
	private String jpn_name;	// Japanese name; unused at the moment
	
	/* Default Constructor */
	public SkillTree() {
		this.id = -1;
		this.name = "";
		this.jpn_name = "";
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
	
}
