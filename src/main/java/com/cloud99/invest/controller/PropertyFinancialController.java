package com.cloud99.invest.controller;

import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.services.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/users/properties")
public class PropertyFinancialController implements Controller {

	@Autowired
	private PropertyService propertyService;

	@GetMapping(path = "/{propertyId}/financials", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseBody
	public PropertyFinances getPropertyFinances(@PathVariable String propertyId) throws Exception {

		return propertyService.getPropertyFinancials(propertyId);
	}

	@PostMapping(path = "/{propertyId}/financials", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public PropertyFinances createPropertyFinances(@PathVariable String propertyId, @RequestBody PropertyFinances propFinances) {

		return propertyService.createPropertyFinances(propertyId, propFinances);
	}

	@GetMapping(path = "/{propertyId}/", produces = JSON_MEDIA_TYPE)
	public void calcualteFlip() {
		// TODO - NG - implement me!
	}
}
