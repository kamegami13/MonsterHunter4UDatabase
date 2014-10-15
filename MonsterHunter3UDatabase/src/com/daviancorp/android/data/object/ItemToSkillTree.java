package com.daviancorp.android.data.object;

/*
 * Class for ItemToSkillTree
 */
public class ItemToSkillTree {

	private long id;				// Id
	private Item item;				// Item
	private SkillTree skillTree;	// SkillTree
	private int points;				// # of points in SkillTree
	
	/* Default Constructor */
	public ItemToSkillTree() {
		this.id = -1;
		this.item = null;
		this.skillTree = null;
		this.points = -1;
	}

	/* Getters and Setters */
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
