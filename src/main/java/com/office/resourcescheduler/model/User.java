package com.office.resourcescheduler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.office.resourcescheduler.errorhandler.ValidationServletException;
import com.office.resourcescheduler.util.Gender;
import com.office.resourcescheduler.util.PojoSavable;
import com.office.resourcescheduler.util.Roles;

@Entity()
@Table(name = "rs_user")
public class User implements PojoSavable<Void> {

	public User() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rs_user_id", length = 11)
	private Long userId;

	@Column(name = "name", length = 100, nullable = false)
	private String userName;

	@Column(name = "email_address", length = 50, nullable = false)
	private String emailAddress;

	@Column(name = "password", length = 30, nullable = false)
	private String password;

	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "is_permanent", nullable = false)
	private boolean isPermanent;

	@Column(name = "role", length = 10)
	@Enumerated(EnumType.STRING)
	private Roles role;

	@Column(name = "gender", length = 1)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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

	public boolean isPermanent() {
		return isPermanent;
	}

	public void setPermanent(boolean isPermanent) {
		this.isPermanent = isPermanent;
	}

	public User(Long userId, String userName, String emailAddress, String password, boolean isActive,
			boolean isPermanent, Roles role, Gender gender) {
		this.userId = userId;
		this.userName = userName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.isActive = isActive;
		this.isPermanent = isPermanent;
		this.role = role;
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", emailAddress=" + emailAddress + ", password="
				+ password + ", isActive=" + isActive + ", role=" + role + ", gender=" + gender + "]";
	}

	@Override
	public void sanitize() {
		setUserName(StringUtils.trimToNull(getUserName()));
		setEmailAddress(StringUtils.trimToNull(getEmailAddress()));
	}

	@Override
	public void validate(Void variable) throws ValidationServletException {

		List<String> error = new ArrayList<>();

		if (StringUtils.isBlank(getUserName())) {
			error.add("Please enter name.");
		}

		if (StringUtils.isNotBlank(getUserName())
				&& !Pattern.matches("^[a-zA-Z]{1,60}\\s?[a-zA-Z]{1,30}$", getUserName())) {
			error.add("Please enter valid name.");
		}

		if (StringUtils.isBlank(getEmailAddress())) {
			error.add("Please enter email.");
		}

		if (StringUtils.isNotBlank(getEmailAddress())
				&& !Pattern.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", getEmailAddress())) {
			error.add("Please enter valid email.");
		}

		if (!error.isEmpty()) {
			throw new ValidationServletException(error);
		}
	}

}
