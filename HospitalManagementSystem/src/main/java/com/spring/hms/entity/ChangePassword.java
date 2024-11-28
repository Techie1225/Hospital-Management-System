package com.spring.hms.entity;


public class ChangePassword {

	private Integer id;
	private String currentpassword;
	private String newpassword;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCurrentpassword() {
		return currentpassword;
	}
	public void setCurrentpassword(String currentpassword) {
		this.currentpassword = currentpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
}

