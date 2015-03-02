package com.daviancorp.android.data.classes;

/*
 * Class for Weapon
 */
public class Weapon extends Item{

	private String wtype;						// Weapon type
	private int creation_cost;					// Cost to create
	private int upgrade_cost;					// Cost to upgrade
	private int attack;							// Attack damage
	private int max_attack;						// Max attack damage; unused at the moment
	private String element;			            // Elemental type
	private String awaken;                  	// Awakened elmeental type
    private String element_2;                   // Second element type
    private long element_attack;
    private long element_2_attack;
    private long awaken_attack;
	private int defense;						// Defense
	private String sharpness;					// Sharpness values
	private String affinity;				    // Affinity
	private String horn_notes;					// Horn notes
	private String shelling_type;				// Shelling type
	private String phial;						// Phial type
	private String charges;						// Charges for bows
	private String coatings;					// Coatings for bows
	private String recoil;						// Recoils for bowguns; arc for bows
	private String reload_speed;				// Reload speed for bowguns
	private String rapid_fire;					// Rapid fire/crouching fire for bowguns
	private String deviation;					// Deviation for bowguns
	private String ammo;						// Ammo for bowguns
	private int num_slots;						// Number of slots
	private String sharpness_file;				// Sharpness image file location
	private int wfinal;							// Final in weapon tree or not
    private int tree_depth;                     // Depth of weapon in weapon tree
	
	/* Default Constructor */
	public Weapon() {
		super();
		
		this.wtype = "";
		this.creation_cost = -1;
		this.upgrade_cost = -1;
		this.attack = -1;
		this.max_attack = -1;
		this.element = "";
        this.element_2 = "";
        this.awaken = "";
		this.defense = -1;
		this.sharpness = "";
		this.affinity = "";
		this.horn_notes = "";
		this.shelling_type = "";
		this.phial = "";
		this.charges = "";
		this.coatings = "";
		this.recoil = "";
		this.reload_speed = "";
		this.rapid_fire = "";
		this.deviation = "";
		this.ammo = "";
		this.num_slots = -1;
		this.sharpness_file = "";
		this.wfinal = -1;
        this.tree_depth = 0;
	}

	/* Getters and Setters */
	public String getWtype() {
		return wtype;
	}

	public void setWtype(String wtype) {
		this.wtype = wtype;
	}

	public int getCreationCost() {
		return creation_cost;
	}

	public void setCreationCost(int creation_cost) {
		this.creation_cost = creation_cost;
	}

	public int getUpgradeCost() {
		return upgrade_cost;
	}

	public void setUpgradeCost(int upgrade_cost) {
		this.upgrade_cost = upgrade_cost;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getMaxAttack() {
		return max_attack;
	}

	public void setMaxAttack(int max_attack) {
		this.max_attack = max_attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public String getSharpness() {
		return sharpness;
	}

	public void setSharpness(String sharpness) {
		this.sharpness = sharpness;
	}

	public String getAffinity() {
		return affinity;
	}

	public void setAffinity(String affinity) {
		this.affinity = affinity;
	}

	public String getHornNotes() {
		return horn_notes;
	}

	public void setHornNotes(String horn_notes) {
		this.horn_notes = horn_notes;
	}

	public String getShellingType() {
		return shelling_type;
	}

	public void setShellingType(String shelling_type) {
		this.shelling_type = shelling_type;
	}
	
	public String getPhial() {
		return phial;
	}
	
	public void setPhial(String phial) {
		this.phial = phial;
	}

	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;
	}

	public String getCoatings() {
		return coatings;
	}

	public void setCoatings(String coatings) {
		this.coatings = coatings;
	}

	public String getRecoil() {
		return recoil;
	}

	public void setRecoil(String recoil) {
		this.recoil = recoil;
	}

	public String getReloadSpeed() {
		return reload_speed;
	}

	public void setReloadSpeed(String reload_speed) {
		this.reload_speed = reload_speed;
	}

	public String getRapidFire() {
		return rapid_fire;
	}

	public void setRapidFire(String rapid_fire) {
		this.rapid_fire = rapid_fire;
	}

	public String getDeviation() {
		return deviation;
	}

	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}

	public String getAmmo() {
		return ammo;
	}

	public void setAmmo(String ammo) {
		this.ammo = ammo;
	}

	public int getNumSlots() {
		return num_slots;
	}

	public void setNumSlots(int num_slots) {
		this.num_slots = num_slots;
	}
	
	public String getSharpnessFile() {
		return sharpness_file;
	}
	
	public void setSharpnessFile(String sharpness_file) {
		this.sharpness_file = sharpness_file;
	}
	
	public int getWFinal() {
		return wfinal;
	}
	
	public void setWFinal(int wfinal) {
		this.wfinal = wfinal;
	}

    public int getTree_Depth() {
        return tree_depth;
    }

    public void setTree_Depth(int tree_depth) {
        this.tree_depth = tree_depth;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getAwaken() {
        return awaken;
    }

    public void setAwaken(String awaken) {
        this.awaken = awaken;
    }

    public String getElement2() {
        return element_2;
    }

    public void setElement2(String element_2) {
        this.element_2 = element_2;
    }

    public long getElementAttack() {
        return element_attack;
    }

    public void setElementAttack(long element_attack) {
        this.element_attack = element_attack;
    }

    public long getElement2Attack() {
        return element_2_attack;
    }

    public void setElement2Attack(long element_2_attack) {
        this.element_2_attack = element_2_attack;
    }

    public long getAwakenAttack() {
        return awaken_attack;
    }

    public void setAwakenAttack(long awaken_attack) {
        this.awaken_attack = awaken_attack;
    }
}
