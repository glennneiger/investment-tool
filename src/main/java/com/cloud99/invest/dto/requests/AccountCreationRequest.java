package com.cloud99.invest.dto.requests;

import com.cloud99.invest.domain.Person.Gender;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.SubscriptionType;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

public class AccountCreationRequest {

	@Getter
	@Setter
	@NotNull(message = "subscription.type.required")
	private SubscriptionType subscription = SubscriptionType.FREE;

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
	private Locale locale = Locale.getDefault();

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
}
