package com.daviancorp.android.data.classes;

/*
 * Class for Monster
 */
public class Monster {

    private long id;                // Monster id
    private String name;            // Monster name
    private String monsterClass;    // Large / small
    private String trait;            // Traits/different type
    private String file_location;    // File location for image
    private String signature_move; // Signature moves

    /* Default Constructor */
    public Monster() {
        this.id = -1;
        this.name = "";
        this.monsterClass = "";
        this.trait = "";
        this.file_location = "";
        this.signature_move = "";
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

    public void setSignatureMove(String signature_move) {
        this.signature_move = signature_move;
    }

    public String getSignatureMove() {
        return signature_move;
    }

}
