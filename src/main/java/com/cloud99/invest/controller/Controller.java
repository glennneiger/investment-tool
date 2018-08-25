package com.cloud99.invest.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface Controller {

	public static final String JSON = MediaType.APPLICATION_JSON_VALUE;
}
