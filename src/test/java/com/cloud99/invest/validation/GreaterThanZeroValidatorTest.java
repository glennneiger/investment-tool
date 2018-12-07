package com.cloud99.invest.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cloud99.invest.MockitoTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class GreaterThanZeroValidatorTest extends MockitoTest {

	private GreaterThanZeroValidator validator;

	@BeforeEach
	public void setup() {
		validator = new GreaterThanZeroValidator();
	}

	@Test
	public void isValid_null() {
		assertFalse(validator.isValid(null, null));
	}

	@Test
	public void isValid_true() {
		assertTrue(validator.isValid(BigDecimal.valueOf(.1), null));
	}

	@Test
	public void isValid_false() {
		assertFalse(validator.isValid(BigDecimal.valueOf(-1), null));
	}
}
