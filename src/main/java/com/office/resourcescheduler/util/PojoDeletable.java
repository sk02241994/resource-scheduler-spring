package com.office.resourcescheduler.util;

import com.office.resourcescheduler.errorhandler.ValidationServletException;

public interface PojoDeletable<T> {

  public void validateDelete(T variable) throws ValidationServletException; 
}
