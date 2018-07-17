package com.cloud99.invest.domain;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public abstract class Person {

	public static enum Gender {
		MALE, FEMALE
	}

	@Valid
	@NotNull(message = "person.name.required")
	private Name personName;

	private Gender gender;

	private LocalDate birthDate;
	private DateTime createDate;
	private DateTime updateDate;

	/**
	 * This method cannot be named just "getName()" since that conflicts with the
	 * {@link Authentication} interface used by our internal {@link User}
	 */
	public Name getPersonName() {
		return personName;
	}

	public void setName(Name name) {
		this.personName = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

}
