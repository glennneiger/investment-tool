package com.cloud99.invest.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface Controller {

	public static final String JSON_MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
	public static final String TEXT_PLAIN_TYPE = MediaType.TEXT_PLAIN_VALUE;
}
