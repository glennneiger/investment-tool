package com.cloud99.invest.controller;

import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.services.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/users/properties")
public class PropertyController {

	@Autowired
	private PropertyService propertyService;

//	@GetMapping("/")
//	@ResponseBody
//	public Iterable<Property> getPropertiesByUser(@RequestParam String userEmail) throws Exception {
//
//		return propertyService.getPropertyByUser(userEmail);
//	}
	
	@PostMapping("/")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Property createProperty(@Valid SingleFamilyProperty property) {

		return propertyService.createProperty(property);
	}
	
	@PutMapping("/")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Property updateProperties(@Valid Property property) {

		return propertyService.updateProperty(property);
	}

	@DeleteMapping("/")
	public void deleteProperty(Property property) {

		propertyService.deleteProperty(property);
	}

	@PostMapping("/financial")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createFinancialPropertyData(PropertyFinances cashFlow) {

		propertyService.createFinancialPropertyData(cashFlow);
	}
}
