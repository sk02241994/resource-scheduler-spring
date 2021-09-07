package com.office.resourcescheduler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.office.resourcescheduler.errorhandler.ValidationException;

public class PasswordValidate {

	public static void validatePassword(String password, String changePassword) throws ValidationException {
		
		List<String> errors = new ArrayList<>();
		
		if(!StringUtils.equals(password, changePassword)) {
			errors.add("Password does not match. Please try again");
		}
		
		if(!Pattern.matches("^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$", password)) {
			errors.add("Password must contain at least one letter, at least one number");
		}
		if(!errors.isEmpty()) {
			throw new ValidationException(errors);
		}
	}
}
