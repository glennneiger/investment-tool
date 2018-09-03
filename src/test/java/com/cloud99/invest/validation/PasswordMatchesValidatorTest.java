package com.cloud99.invest.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cloud99.invest.BaseMockitoTest;
import com.cloud99.invest.dto.requests.AccountCreationRequest;

import org.junit.jupiter.api.Test;

public class PasswordMatchesValidatorTest extends BaseMockitoTest {

	@Test
	public void testValidate_matches() {
		PasswordMatchesValidator v = new PasswordMatchesValidator();
		AccountCreationRequest request = new AccountCreationRequest();
		String pwd = "P@ssw0rd";
		request.setPassword(pwd);
		request.setMatchingPassword(pwd);

		assertTrue(v.isValid(request, null));
	}

	@Test
	public void testValidate_fail() {
		PasswordMatchesValidator v = new PasswordMatchesValidator();
		AccountCreationRequest request = new AccountCreationRequest();

		request.setPassword("P@ssw0rd");
		request.setMatchingPassword("SomeOtherPassword");

		assertFalse(v.isValid(request, null));
	}

	@Test
	public void testValidate_null_password_fail() {
		PasswordMatchesValidator v = new PasswordMatchesValidator();
		AccountCreationRequest request = new AccountCreationRequest();

		request.setPassword(null);
		request.setMatchingPassword("SomeOtherPassword");

		assertFalse(v.isValid(request, null));
	}

	@Test
	public void testValidate_null_matchingPassword_fail() {
		PasswordMatchesValidator v = new PasswordMatchesValidator();
		AccountCreationRequest request = new AccountCreationRequest();

		request.setPassword("Password");
		request.setMatchingPassword(null);

		assertFalse(v.isValid(request, null));
	}

	@Test
	public void testValidate_null_passwords_fail() {
		PasswordMatchesValidator v = new PasswordMatchesValidator();
		AccountCreationRequest request = new AccountCreationRequest();

		request.setPassword(null);
		request.setMatchingPassword(null);

		assertFalse(v.isValid(request, null));
	}
}
