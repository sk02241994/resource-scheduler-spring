package com.office.resourcescheduler.controller;

import java.util.Optional;

import com.office.resourcescheduler.model.User;
import com.office.resourcescheduler.service.UserServiceImpl;
import com.office.resourcescheduler.util.FormTransform;
import com.office.resourcescheduler.util.Gender;
import com.office.resourcescheduler.util.Roles;

public class UserForm implements FormTransform<User, Void> {

	private Long userId;
	private String name;
	private String emailAddress;
	private boolean isActive;
	private boolean isAdmin;
	private boolean isPermanentEmployee;
	private String gender;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public boolean isPermanentEmployee() {
		return isPermanentEmployee;
	}

	public void setPermanentEmployee(boolean isPermanentEmployee) {
		this.isPermanentEmployee = isPermanentEmployee;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public UserForm() {
	}

	public UserForm(Long userId, String name, String emailAddress, boolean isActive, boolean isAdmin,
			boolean isPermanentEmployee, String gender) {
		this.userId = userId;
		this.name = name;
		this.emailAddress = emailAddress;
		this.isActive = isActive;
		this.isAdmin = isAdmin;
		this.isPermanentEmployee = isPermanentEmployee;
		this.gender = gender;
	}

	@Override
	public User transform(Void arg) {
        Optional<User> optionalUser = UserServiceImpl.getInstance().findById(userId == null ? 0 : userId);
        String password = "user";
        if(optionalUser.isPresent()){
            password = optionalUser.get().getPassword();
        }
		return new User(userId, name, emailAddress, password, isActive, isPermanentEmployee,
				isAdmin ? Roles.ADMIN : Roles.USER, getGenderFromForm());
	}

	private Gender getGenderFromForm() {
		if (getGender() == null)
			return null;
		if ("M".equals(getGender()))
			return Gender.M;
		else
			return Gender.F;
	}
}
