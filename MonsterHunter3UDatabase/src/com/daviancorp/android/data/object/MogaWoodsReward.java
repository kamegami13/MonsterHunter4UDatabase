package com.daviancorp.android.data.object;

public class MogaWoodsReward {

	private long id;
	private Monster monster;
	private String time;
	private Item item;
	private int commodity_stars;
	private int kill_percentage;
	private int capture_percentage;
	
	public MogaWoodsReward() {
		this.id = -1;
		this.monster = null;
		this.time = "";
		this.item = null;
		this.commodity_stars = -1;
		this.kill_percentage = -1;
		this.capture_percentage = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getCommodityStars() {
		return commodity_stars;
	}

	public void setCommodityStars(int commodity_stars) {
		this.commodity_stars = commodity_stars;
	}

	public int getKillPercentage() {
		return kill_percentage;
	}

	public void setKillPercentage(int kill_percentage) {
		this.kill_percentage = kill_percentage;
	}

	public int getCapturePercentage() {
		return capture_percentage;
	}

	public void setCapturePercentage(int capture_percentage) {
		this.capture_percentage = capture_percentage;
	}
}
