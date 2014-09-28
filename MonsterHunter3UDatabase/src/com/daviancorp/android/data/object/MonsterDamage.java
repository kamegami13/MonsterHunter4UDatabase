package com.daviancorp.android.data.object;

public class MonsterDamage {

	private long id;
//	private Monster monster;
	private String body_part;
	private int cut;
	private int impact;
	private int shot;
	private int fire;
	private int water;
	private int ice;
	private int thunder;
	private int dragon;
	private int ko;
	
	public MonsterDamage() {
		this.id = -1;
		this.body_part = "";
		this.cut = -1;
		this.impact = -1;
		this.shot = -1;
		this.fire = -1;
		this.water = -1;
		this.ice = -1;
		this.thunder = -1;
		this.dragon = -1;
		this.ko = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBodyPart() {
		return body_part;
	}

	public void setBodyPart(String body_part) {
		this.body_part = body_part;
	}

	public int getCut() {
		return cut;
	}

	public void setCut(int cut) {
		this.cut = cut;
	}

	public int getImpact() {
		return impact;
	}

	public void setImpact(int impact) {
		this.impact = impact;
	}

	public int getShot() {
		return shot;
	}

	public void setShot(int shot) {
		this.shot = shot;
	}

	public int getFire() {
		return fire;
	}

	public void setFire(int fire) {
		this.fire = fire;
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public int getIce() {
		return ice;
	}

	public void setIce(int ice) {
		this.ice = ice;
	}

	public int getThunder() {
		return thunder;
	}

	public void setThunder(int thunder) {
		this.thunder = thunder;
	}

	public int getDragon() {
		return dragon;
	}

	public void setDragon(int dragon) {
		this.dragon = dragon;
	}

	public int getKo() {
		return ko;
	}

	public void setKo(int ko) {
		this.ko = ko;
	}
}
