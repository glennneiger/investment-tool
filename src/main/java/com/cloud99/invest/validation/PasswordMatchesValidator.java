package com.cloud99.invest.validation;

import com.cloud99.invest.dto.requests.AccountCreationRequest;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, AccountCreationRequest> {

	@Override
	public boolean isValid(AccountCreationRequest request, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(request.getPassword()) || StringUtils.isEmpty(request.getMatchingPassword())) {
			return false;
		}
		return request.getPassword().equals(request.getMatchingPassword());
	}

}
