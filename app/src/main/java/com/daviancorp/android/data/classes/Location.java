package com.daviancorp.android.data.classes;

/*
 * Class for Location
 */
public class Location {
	
	private long id;				// Location id
	private String name;			// Location name
	private String file_location;	// File location for image
    private String file_location_mini; // File location for small image
	
	/* Default Constructor */
	public Location() {
		this.id = -1;
		this.name = "";
		this.file_location = "";
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

	public String getFileLocation() {
		return file_location;
	}

	public void setFileLocation(String file_location) {
		this.file_location = file_location;
	}

    public String getFileLocationMini() {
        return file_location_mini;
    }

    public void setFileLocationMini(String file_location_mini) {
        this.file_location_mini = file_location_mini;
    }
}
