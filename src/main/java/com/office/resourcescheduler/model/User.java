package com.office.resourcescheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rs_user")
public class User {

	public User() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rs_user_id", length = 11)
	private Integer userId;

	@Column(name = "name", length = 100, nullable = false)
	private String userName;

	@Column(name = "email_address", length = 50, nullable = false)
	private String emailAddress;

	@Column(name = "password", length = 30, nullable = false)
	private String password;

	@Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
	private boolean isActive;

	@Column(name = "is_admin", columnDefinition = "tinyint(1) default 0")
	private boolean isAdmin;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User(Integer userId, String userName, String emailAddress, String password, boolean isActive,
			boolean isAdmin) {
		this.userId = userId;
		this.userName = userName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.isActive = isActive;
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", emailAddress=" + emailAddress + ", password="
				+ password + ", isActive=" + isActive + ", isAdmin=" + isAdmin + "]";
	}

}
