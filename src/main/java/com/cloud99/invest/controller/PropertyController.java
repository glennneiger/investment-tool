package com.cloud99.invest.controller;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.services.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/properties")
public class PropertyController {


	@Autowired
	private PropertyService propertyService;

	@GetMapping("/")
	@ResponseBody
	public Address test(@RequestParam String name) throws Exception {

		return null;
	}
	
	@PostMapping("/")
	public Property createNewProperty(@Valid Property property, BindingResult result,
			HttpServletRequest request,
			Errors errors) {

		return null;
	
	}
}
