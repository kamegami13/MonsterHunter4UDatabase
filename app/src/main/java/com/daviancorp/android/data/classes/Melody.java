package com.daviancorp.android.data.classes;

/*
 * Class for Melody
 */
public class Melody {

    private long id;				// id
    private String notes;           // notes available
    private String song;            // song available
    private String effect1;         // initial effect
    private String effect2;         // encore effect
    private String duration;        // duration
    private String extension;       // encore duration

    /* Default Constructor */
    public Melody() {
        this.id = -1;
        this.notes = "";
        this.song = "";
        this.effect1 = "";
        this.effect2 = "";
        this.duration = "";
        this.extension = "";
    }

    /* Getters and Setters */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getEffect1() {
        return effect1;
    }

    public void setEffect1(String effect1) {
        this.effect1 = effect1;
    }

    public String getEffect2() {
        return effect2;
    }

    public void setEffect2(String effect2) {
        this.effect2 = effect2;
    }

    public String getDuration(){
        return duration;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public String getExtension(){
        return extension;
    }

    public void setExtension(String extension){
        this.extension = extension;
    }

}
