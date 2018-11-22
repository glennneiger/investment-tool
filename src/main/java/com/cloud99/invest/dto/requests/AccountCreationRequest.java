package com.cloud99.invest.dto.requests;

import com.cloud99.invest.domain.Person.Gender;
import com.cloud99.invest.domain.account.MembershipType;
import com.cloud99.invest.validation.PasswordMatches;

import org.joda.money.CurrencyUnit;
import org.joda.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

@PasswordMatches
public class AccountCreationRequest {

	@Getter
	@Setter
	@NotNull(message = "account.name.required")
	private String accountName;

	@NotNull(message = "required.email")
	@NotEmpty(message = "required.email")
	@Email(message = "invalid.email")
	@Getter
	@Setter
	private String email;

	@NotNull(message = "password.required")
	@NotEmpty(message = "password.required")
	@Getter
	@Setter
	private String password;

	@NotNull(message = "password.required")
	@NotEmpty(message = "password.required")
	@Getter
	@Setter
	private String matchingPassword;

	@Getter
	@Setter
	private Locale locale;

	@NotNull(message = "name.first.required")
	@Getter
	@Setter
	private String firstName;

	@NotNull(message = "name.last.required")
	@Getter
	@Setter
	private String lastName;

	@Getter
	@Setter
	private String middleName;

	@Getter
	@Setter
	private Gender gender;

	@Getter
	@Setter
	private LocalDate birthDate;

	@Getter
	@Setter
	private CurrencyUnit currency;

}
