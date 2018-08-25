package com.cloud99.invest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler { // extends ResponseEntityExceptionHandler {

	// @Override
	// protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object
	// body, HttpHeaders headers, HttpStatus status, WebRequest request) {
	// return super.handleExceptionInternal(ex, body, headers, status, request);
	// }

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(value = AccessDeniedException.class)
	public void handleAccessDenitedException(HttpServletRequest req, Exception ex) {
		log.error("Access denied exception encountered: " + ex.getMessage(), ex);

	}
	// TODO - NG - implement handler for all JSR 303 validation exceptions and throw
	// ServiceError object

}
