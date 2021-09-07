package com.office.resourcescheduler.util;

public interface FormTransform<T, I> {

	public T transform(I arg);
}
