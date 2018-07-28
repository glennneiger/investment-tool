package com.cloud99.invest.controller;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.dto.PropertySearchRequest;
import com.cloud99.invest.dto.PropertyValuationResult;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.ServiceProvider;
import com.cloud99.invest.integration.ServiceProviderFactory;
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

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/users")
public class PropertyController implements Controller {

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private UserService userService;

	@Autowired
	private ServiceProviderFactory providerFactory;

	@Autowired
	private ServiceProvider serviceProvider;

	@PostConstruct
	public void init() {
		serviceProvider = providerFactory.getServiceProvider(ProviderInfo.ZILLOW);
	}

	@PostMapping(consumes = JSON, produces = JSON, path = "/property/valuation")
	@ResponseBody
	public PropertyValuationResult lookupPropertyValuation(@RequestBody @Valid PropertySearchRequest request) throws Exception {

		return serviceProvider.propertyValuation(request);

	}

	@GetMapping(path = "/{userEmail}/properties", consumes = JSON, produces = JSON)
	@ResponseBody
	public Iterable<Property> getPropertiesByUser(@PathVariable String userEmail) throws Exception {

		return propertyService.getPropertyDetails(userEmail);
	}
	
	@PostMapping(path = "/{userEmail}/properties", consumes = JSON, produces = JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public <T extends Property> T createProperty(@RequestBody @Valid T property, @PathVariable String userEmail) {

		return propertyService.createProperty(userEmail, property);
	}

	@PutMapping(path = "/{userEmail}/property/{propertyId}", consumes = JSON, produces = JSON)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Property updateProperties(@PathVariable String userEmail, @PathVariable String propertyId, @Valid Property property) {

		property.setId(propertyId);
		return propertyService.updateProperty(userEmail, property);
	}

	@DeleteMapping(path = "/{userEmail}/property/{propertyId}", consumes = JSON)
	public void deleteProperty(@PathVariable String userEmail, @PathVariable String propertyId) {

		propertyService.deleteProperty(userEmail, propertyId);
	}

}
