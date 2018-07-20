package com.cloud99.invest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class Person {

	public static enum Gender {
		MALE, FEMALE
	}

	@Valid
	@NotNull(message = "person.name.required")
	@Getter
	@Setter
	// This method cannot be named just "getName()" since that conflicts with the
	// {@link Authentication} interface used by our internal {@link User}
	@JsonProperty("name")
	private Name personName;

	@Getter
	@Setter
	private Gender gender;

	@Getter
	@Setter
	private LocalDate birthDate;

	@Getter
	@Setter
	private DateTime createDate;
	
	@Getter
	@Setter
	private DateTime updateDate;


}
