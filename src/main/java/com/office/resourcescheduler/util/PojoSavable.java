package com.office.resourcescheduler.util;

import com.office.resourcescheduler.errorhandler.ValidationException;

public interface PojoSavable<T> {

  public void sanitize();

  public void validate(T variable) throws ValidationException;

  default void sanitizeAndValidate(T variable) throws ValidationException {
    sanitize();
    validate(variable);
  }
  
  default void sanitizeAndValidate() throws ValidationException {
    sanitize();
    validate(null);
  }
}
