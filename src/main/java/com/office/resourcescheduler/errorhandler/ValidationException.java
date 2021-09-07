package com.office.resourcescheduler.errorhandler;

import java.util.Collection;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private final Collection<String> error;

	public ValidationException(Collection<String> error) {
		super("Validation exception thrown");
		this.error = error;
	}

	public Collection<String> getError() {
		return this.error;
	}

}