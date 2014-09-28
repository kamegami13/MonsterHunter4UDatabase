package com.daviancorp.android.data.object;

public class ItemToSkillTree {

	private long id;
	private Item item;
	private SkillTree skillTree;
	private int points;
	
	public ItemToSkillTree() {
		this.id = -1;
		this.item = null;
		this.skillTree = null;
		this.points = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public SkillTree getSkillTree() {
		return skillTree;
	}

	public void setSkillTree(SkillTree skillTree) {
		this.skillTree = skillTree;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
}
