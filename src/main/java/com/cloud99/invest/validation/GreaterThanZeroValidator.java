package com.cloud99.invest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class GreaterThanZeroValidator implements ConstraintValidator<GreaterThanZero, BigDecimal> {

	@Override
	public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {

		if (value == null) {
			return false;
		}

		if (value.doubleValue() <= 0) {
			return false;
		}

		return true;
	}

}
