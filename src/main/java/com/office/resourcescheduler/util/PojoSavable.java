package com.office.resourcescheduler.util;

import com.office.resourcescheduler.errorhandler.ValidationServletException;

public interface PojoSavable<T> {

  public void sanitize();

  public void validate(T variable) throws ValidationServletException;

  default void sanitizeAndValidate(T variable) throws ValidationServletException {
    sanitize();
    validate(variable);
  }
  
  default void sanitizeAndValidate() throws ValidationServletException {
    sanitize();
    validate(null);
  }
}
