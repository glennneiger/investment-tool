package com.cloud99.invest.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class ValidationException {

	@Getter
	private final HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;

	@Getter
	private final String transactionId = UUID.randomUUID().toString();

	@Getter
	private List<ValidationMessage> fieldMessages;

	@Getter
	private List<ValidationMessage> globalMessages;

	public ValidationException(List<ValidationMessage> fieldMessages, List<ValidationMessage> globalMessages) {
		this.fieldMessages = fieldMessages;
		this.globalMessages = globalMessages;
	}

}
