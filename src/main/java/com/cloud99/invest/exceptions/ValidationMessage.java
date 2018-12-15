package com.cloud99.invest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Holds internationalized message regarding failed validation in regards to a
 * specific field/attribute in a request payload. These messages are stored
 * within a {@link ValidationException} and are returned to the API client via
 * json
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ValidationMessage {

	@Getter
	private String attribute;

	@Getter
	private String message;
}
