package com.daviancorp.android.data.object;

public class Monster {

	private long id;
	private String name;
	private String monsterClass;
	private String trait;
	private String file_location;
	
	public Monster() {
		this.id = -1;
		this.name = "";
		this.monsterClass = "";
		this.trait = "";
		this.file_location = "";
	}
	
	public Monster(long id, String name, String monsterClass, String trait) {
		this.id = id;
		this.name = name;
		this.monsterClass = monsterClass;
		this.trait = trait;
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

	public String getMonsterClass() {
		return monsterClass;
	}

	public void setMonsterClass(String monsterClass) {
		this.monsterClass = monsterClass;
	}

	public String getTrait() {
		return trait;
	}

	public void setTrait(String trait) {
		this.trait = trait;
	}
	
	public String getFileLocation() {
		return file_location;
	}
	
	public void setFileLocation(String file_location) {
		this.file_location = file_location;
	}
	
}
