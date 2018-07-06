package com.cloud99.invest.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;

public class Name {

	@NotNull(message = "name.first.required")
	private String firstName;

	@NotNull(message = "name.last.required")
	private String lastName;

	private String middleName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getFirstName()).append(getLastName()).append(getMiddleName()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Name rhs = (Name) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(getFirstName(), rhs.getFirstName())
				.append(getLastName(), rhs.getLastName())
				.append(getMiddleName(), rhs.getMiddleName()).
				isEquals();
	}


}
