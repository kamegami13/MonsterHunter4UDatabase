package com.daviancorp.android.data.object;

/*
 * Note: No constructor with fields
 * Note: Add skills
 */
public class Decoration extends Item{

	private int num_slots;
	private long skill_1_id;
	private String skill_1_name;
	private int skill_1_point;
	private long skill_2_id;
	private String skill_2_name;
	private int skill_2_point;
	
	public Decoration() {
		super();
		
		this.num_slots = -1;
		this.skill_1_id = -1;
		this.skill_1_name = "";
		this.skill_1_point = -1;
		this.skill_2_id = -1;
		this.skill_2_name = "";
		this.skill_2_point = -1;
	}
	
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
