package com.cloud99.invest.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Name {

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

}
