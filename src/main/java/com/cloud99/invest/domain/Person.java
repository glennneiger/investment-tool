package com.cloud99.invest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain object representation of a person
 */
@SuppressWarnings("serial")
@NoArgsConstructor
public abstract class Person implements Serializable {

	/**
	 * Enum for setting the gender on a person
	 */
	public enum Gender {
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
