package com.cloud99.invest.validation;

import com.cloud99.invest.domain.User;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, User> {

	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(user.getPassword()) || StringUtils.isEmpty(user.getMatchingPassword())) {
			return false;
		}
		return user.getPassword().equals(user.getMatchingPassword());
	}

}
