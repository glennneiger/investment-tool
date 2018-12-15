package com.cloud99.invest.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Name implements Serializable {

	@Getter
	@Setter
	@NotNull(message = "name.first.required")
	private String firstName;

	@Getter
	@Setter
	@NotNull(message = "name.last.required")
	private String lastName;

	@Getter
	@Setter
	private String middleName;

	public Name(@NotNull(message = "name.first.required") String firstName, String middleName, @NotNull(message = "name.last.required") String lastName) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	@Transient
	public String getFormattedName() {
		return firstName + " " + lastName;
	}

}
