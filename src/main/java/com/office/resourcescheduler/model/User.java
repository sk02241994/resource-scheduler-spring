package com.office.resourcescheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.office.resourcescheduler.util.Gender;
import com.office.resourcescheduler.util.Roles;

@Entity()
@Table(name = "rs_user")
public class User {

	public User() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rs_user_id", length = 11)
	private long userId;

	@Column(name = "name", length = 100, nullable = false)
	private String userName;

	@Column(name = "email_address", length = 50, nullable = false)
	private String emailAddress;

	@Column(name = "password", length = 30, nullable = false)
	private String password;

	@Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
	private boolean isActive;

	@Column(name = "role", length = 10)
	@Enumerated(EnumType.STRING)
	private Roles role;

	@Column(name = "gender", length = 1)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Roles getRole() {
		return this.role;
	}

	public User(long userId, String userName, String emailAddress, String password, boolean isActive, Roles role,
			Gender gender) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.isActive = isActive;
		this.role = role;
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", emailAddress=" + emailAddress + ", password="
				+ password + ", isActive=" + isActive + ", role=" + role + ", gender=" + gender + "]";
	}

	
}
