package com.office.resourcescheduler.util;

public enum Roles {

	ADMIN("ADMIN"),
	USER("USER");

	private final String role;
	
	private Roles(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	
}
