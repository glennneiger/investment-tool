package com.cloud99.invest.exceptions;

import com.cloud99.invest.controller.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

/**
 * This exception class is the external facing exception that will be created by
 * the {@link GlobalExceptionHandler} and ultimately returned to the API client.
 */
public class ApiException extends Exception {

	@Getter
	private HttpStatus httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;

	@Getter
	private String transactionId;

	@Getter
	private List<String> errorsMessages = new ArrayList<>();

	public ApiException(List<String> errorMessages, HttpStatus httpStatusCode) {
		this.errorsMessages = errorMessages;
		this.httpStatusCode = httpStatusCode;
		transactionId = UUID.randomUUID().toString();
	}

}
