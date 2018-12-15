package com.cloud99.invest.controller;

import com.cloud99.invest.exceptions.ValidationMessage;
import com.cloud99.invest.exceptions.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messages;

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO - NG - change this to log and build and API error and send out the door
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	/**
	 * Invoked when {@link Valid} or {@link Validated} are triggered
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ValidationMessage> fieldErrors = new ArrayList<>();
		List<ValidationMessage> globalErrors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			fieldErrors.add(addInternationalizedMessage(request, error.getDefaultMessage(), error.getField()));
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			globalErrors.add(addInternationalizedMessage(request, error.getDefaultMessage(), error.getObjectName()));
		}
		ValidationException validationException = new ValidationException(fieldErrors, globalErrors);
		return new ResponseEntity<>(validationException, validationException.getHttpStatusCode());
	}

	private ValidationMessage addInternationalizedMessage(WebRequest request, String errorCode, String attribute) {
		return new ValidationMessage(attribute, messages.getMessage(errorCode, new Object[0], request.getLocale()));
	}

}
