package com.daviancorp.android.data.classes;

/*
 * Class for Skill
 */
public class Skill {

	private long id;				// id
//	private SkillTree skillTree;	// SkillTree; unused at the moment
	private int required_points;	// Required points to unlock skill
	private String name;			// Skill name
	private String jpn_name;		// Japanese skill name; unused at the moment
	private String description;		// Skill description
	
	/* Default Constructor */
	public Skill() {
		this.id = -1;
		this.required_points = -1;
		this.name = "";
		this.jpn_name = "";
		this.description = "";
	}

	/* Getters and Setters */
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
