package com.daviancorp.android.data.classes;

/*
 * Class for Quest
 */
public class Quest {
	
	private long id;			// id
	private String name;		// Quest name
	private String goal;		// Clear condition
	private String hub;			// Port or village
	private String type;		// Key quest or not
	private String stars;		// # of stars
	private Location location;	// Location
	//private String locationTime;// Day or Night
	private int time_limit;		// Time limit
	private int fee;			// Quest fee
	private int reward;			// Quest reward in zenny
	private int hrp;			// Hunting rank points
    private String sub_goal;		// Subquest Clear condition
    private int sub_reward;			// Subquest reward in zenny
    private int sub_hrp;			// Subquest Hunting rank points
	
	/* Default Constructor */
	public Quest() {
		this.id = -1;
		this.name = "";
		this.goal = "";
		this.hub = "";
		this.type = "";
		this.stars = "";
		this.location = null;
		//this.locationTime = "";
		this.time_limit = -1;
		this.fee = -1;
		this.reward = -1;
		this.hrp = -1;
        this.sub_goal = "";
        this.sub_reward = -1;
        this.sub_hrp = -1;
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

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	/*public String getLocationTime() {
		return locationTime;
	}*/

	/*public void setLocationTime(String locationTime) {
		this.locationTime = locationTime;
	}*/

	public int getTimeLimit() {
		return time_limit;
	}

	public void setTimeLimit(int time_limit) {
		this.time_limit = time_limit;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getHrp() {
		return hrp;
	}

	public void setHrp(int hrp) {
		this.hrp = hrp;
	}

    public String getSubGoal() {
        return sub_goal;
    }

    public void setSubGoal(String sub_goal) {
        this.sub_goal = sub_goal;
    }

    public int getSubReward() {
        return sub_reward;
    }

    public void setSubReward(int sub_reward) {
        this.sub_reward = sub_reward;
    }

    public int getSubHrp() {
        return sub_hrp;
    }

    public void setSubHrp(int sub_hrp) {
        this.sub_hrp = sub_hrp;
    }
	
	@Override
	public String toString(){
		return this.name;
	}
	
}
