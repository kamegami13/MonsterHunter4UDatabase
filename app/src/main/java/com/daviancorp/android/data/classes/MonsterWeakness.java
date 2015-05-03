package com.daviancorp.android.data.classes;

/*
 * Class for Monster Weakness
 *
 * Each element or ailment is set from 0-2
 * 0 is Immune or Ineffective
 * 1 is Slightly effecitve
 * 2 is Very effective
 */

public class MonsterWeakness {

	private long id;			// id
	private String monstername;	// Monster Name
	private String state;		// Monster State
	private int fire;			// Fire element effectiveness (unused)
	private int water;			// Water element effectiveness (unused)
	private int thunder;		// Thunder element effectiveness (unused)
	private int ice;			// Ice element effectiveness (unused)
	private int dragon;			// Dragon element effectiveness (unused)
	private int poison;			// Poison effectiveness
	private int paralysis;		// Paralysis effectiveness
	private int sleep;			// Sleep effectiveness
	private int pitfalltrap;	// Pitfall trap effectiveness
	private int shocktrap;		// Shock trap effectiveness
	private int flashbomb;		// Flash bomb effectiveness
	private int sonicbomb;		// Sonic bomb effectiveness
	private int dungbomb;		// Dung bomp effectiveness
	private int meat;			// Meat effectiveness

	/* Default Constructor */
	public MonsterWeakness() {
		this.id = -1;
		this.monstername = "";
		this.state = "";
		this.fire = -1;
		this.water = -1;
		this.thunder = -1;
		this.ice = -1;
		this.dragon = -1;
		this.poison = -1;
		this.paralysis = -1;
		this.sleep = -1;
		this.pitfalltrap = -1;
		this.shocktrap = -1;
		this.flashbomb = -1;
		this.sonicbomb = -1;
		this.dungbomb = -1;
		this.meat = -1;
	}

	/* Getters and Setters */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMonstername() {
		return monstername;
	}

	public void setMonstername(String monstername) {
		this.monstername = monstername;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public int getThunder() {
		return thunder;
	}

	public void setThunder(int thunder) {
		this.thunder = thunder;
	}

	public int getIce() {
		return ice;
	}

	public void setIce(int ice) {
		this.ice = ice;
	}

	public int getDragon() {
		return dragon;
	}

	public void setDragon(int dragon) {
		this.dragon = dragon;
	}

	public int getPoison() {
		return poison;
	}

	public void setPoison(int poison) {
		this.poison = poison;
	}

	public int getParalysis() {
		return paralysis;
	}

	public void setParalysis(int paralysis) {
		this.paralysis = paralysis;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public int getPitfalltrap() {
		return pitfalltrap;
	}

	public void setPitfalltrap(int pitfalltrap) {
		this.pitfalltrap = pitfalltrap;
	}

	public int getShocktrap() {
		return shocktrap;
	}

	public void setShocktrap(int shocktrap) {
		this.shocktrap = shocktrap;
	}

	public int getFlashbomb() {
		return flashbomb;
	}

	public void setFlashbomb(int flashbomb) {
		this.flashbomb = flashbomb;
	}

	public int getSonicbomb() {
		return sonicbomb;
	}

	public void setSonicbomb(int sonicbomb) {
		this.sonicbomb = sonicbomb;
	}

	public int getDungbomb() {
		return dungbomb;
	}

	public void setDungbomb(int dungbomb) {
		this.dungbomb = dungbomb;
	}

	public int getMeat() {
		return meat;
	}

	public void setMeat(int meat) {
		this.meat = meat;
	}
}
