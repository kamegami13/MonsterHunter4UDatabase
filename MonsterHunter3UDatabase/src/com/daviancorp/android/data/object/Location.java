package com.daviancorp.android.data.object;

public class Location {
	
	private long id;
	private String name;
	private String file_location;
	
	public Location() {
		this.id = -1;
		this.name = "";
		this.file_location = "";
	}

	public Location(long id, String name, String file_location) {
		this.id = id;
		this.name = name;
		this.file_location = file_location;
	}

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
	
}
