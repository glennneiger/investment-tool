package com.cloud99.invest.repo.listeners;

import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

// TODO - NG - need to test this out to see if it really works
public class DbRefFieldCallback implements ReflectionUtils.FieldCallback {

	private boolean idFound;

	@Override
	public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(Id.class)) {
			idFound = true;
		}

	}

	public boolean isIdFound() {
		return idFound;
	}

}
