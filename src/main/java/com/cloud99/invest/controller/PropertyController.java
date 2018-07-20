package com.cloud99.invest.controller;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.services.PropertyService;
import com.cloud99.invest.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/users/properties")
public class PropertyController implements BaseController {

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private UserService userService;

	@GetMapping(path = "/", consumes = JSON, produces = JSON)
	@ResponseBody
	public Iterable<Property> getPropertiesByUser(@RequestParam String userEmail) throws Exception {

		User user = userService.getCurrentSessionUser();
		return propertyService.getPropertyDetails(user.getPropertyRefs());
	}
	
	@PostMapping(path = "/", consumes = JSON, produces = JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public <T extends Property> T createProperty(@RequestBody @Valid T property) {

		return propertyService.createProperty(property);
	}

	@PutMapping(path = "/{propertyId}", consumes = JSON, produces = JSON)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Property updateProperties(@PathVariable String propertyId, @Valid Property property) {

		return propertyService.updateProperty(property);
	}

	@DeleteMapping(path = "/{propertyId}", consumes = JSON)
	public void deleteProperty(@PathVariable String propertyId) {

		propertyService.deleteProperty(propertyId);
	}

}
