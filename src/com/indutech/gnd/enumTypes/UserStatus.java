package com.indutech.gnd.enumTypes;

public enum UserStatus {

	Inactive("0"),
	Active("1");
	
	private String userStatus;
	
	private UserStatus(String userStatus) {
		this.userStatus=userStatus;
	}
	
	public String getUserStatus(){
		return userStatus;
	}
}
