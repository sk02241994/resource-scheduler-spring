package com.office.resourcescheduler.controller;

public class ResetForm {

	private String emailAddress;
	private String token;
	private String password;
	private String confirmPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ResetForm(String emailAddress, String token, String password, String confirmPassword) {
		this.emailAddress = emailAddress;
		this.token = token;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public ResetForm() {
	}

}
