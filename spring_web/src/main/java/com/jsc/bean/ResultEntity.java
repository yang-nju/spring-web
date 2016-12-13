package com.jsc.bean;

public class ResultEntity {

	private String id;
	private String username;
	private Boolean locked;
	
	public ResultEntity(String id, String username, Boolean locked){
		this.id = id;
		this.username = username;
		this.locked = locked;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
}
