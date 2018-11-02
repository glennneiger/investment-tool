package com.cloud99.invest.controller;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;
import com.cloud99.invest.services.RentalPropertyFinancialService;

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
@RequestMapping(path = "/v1/users/properties/rentals")
public class RentalPropertyFinancialController implements Controller {

	@Autowired
	private RentalPropertyFinancialService propertyService;

	@GetMapping(path = "/{propertyId}/financials", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseBody
	public RentalPropertyFinances getPropertyFinances(@PathVariable String propertyId) throws Exception {

		return propertyService.getPropertyFinancials(propertyId);
	}

	@PostMapping(path = "/{propertyId}/financials", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public RentalPropertyFinances createPropertyFinances(@PathVariable String propertyId, @RequestBody RentalPropertyFinances propFinances) {

		propFinances.setPropertyId(propertyId);
		return propertyService.createPropertyFinances(propFinances);
	}

}
