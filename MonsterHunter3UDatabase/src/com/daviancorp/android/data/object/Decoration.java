package com.daviancorp.android.data.object;

/*
 * Class for Decoration
 *
 * Note: Subclass of Item
 */
public class Decoration extends Item{

	private int num_slots;			// Number of slots required
	private long skill_1_id;		// Id of SkillTree 1
	private String skill_1_name;	// Name of SkillTree 1
	private int skill_1_point;		// # of points for SkillTree 1
	private long skill_2_id;		// Id of SkillTree 2
	private String skill_2_name;	// Name of SkillTree 2
	private int skill_2_point;		// # of points for SkillTree 2
	
	/* Default Constructor */
	public Decoration() {
		super();					// Initialized variables in Item
		
		this.num_slots = -1;
		this.skill_1_id = -1;
		this.skill_1_name = "";
		this.skill_1_point = -1;
		this.skill_2_id = -1;
		this.skill_2_name = "";
		this.skill_2_point = -1;
	}
	
	/* Getters and Setters */
	public int getNumSlots() {
		return num_slots;
	}
	
	public void setNumSlots(int num_slots) {
		this.num_slots = num_slots;
	}
	
	public long getSkill1Id() {
		return skill_1_id;
	}
	
	public void setSkill1Id(long skill_1_id) {
		this.skill_1_id = skill_1_id;
	}
	
	public String getSkill1Name() {
		return skill_1_name;
	}
	
	public void setSkill1Name(String skill_1_name) {
		this.skill_1_name = skill_1_name;
	}
	
	public int getSkill1Point() {
		return skill_1_point;
	}
	
	public void setSkill1Point(int skill_1_point) {
		this.skill_1_point = skill_1_point;
	}
	
	public long getSkill2Id() {
		return skill_2_id;
	}
	
	public void setSkill2Id(long skill_2_id) {
		this.skill_2_id = skill_2_id;
	}
	
	public String getSkill2Name() {
		return skill_2_name;
	}
	
	public void setSkill2Name(String skill_2_name) {
		this.skill_2_name = skill_2_name;
	}
	
	public int getSkill2Point() {
		return skill_2_point;
	}
	
	public void setSkill2Point(int skill_2_point) {
		this.skill_2_point = skill_2_point;
	}
	
}
