package com.office.resourcescheduler.util;

import com.office.resourcescheduler.errorhandler.ValidationException;

public interface PojoDeletable<T> {

  public void validateDelete(T variable) throws ValidationException; 
}
