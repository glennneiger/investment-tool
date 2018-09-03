package com.cloud99.invest.controller;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.ServiceProvider;
import com.cloud99.invest.integration.ServiceProviderFactory;
import com.cloud99.invest.services.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;

@RestController
@RequestMapping(path = "/v1")
public class PropertyController implements Controller {

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private ServiceProviderFactory providerFactory;

	@Autowired
	private ServiceProvider serviceProvider;

	@PostConstruct
	public void init() {
		serviceProvider = providerFactory.getServiceProvider(ProviderInfo.ZILLOW);
	}

	// TODO - NG - this needs to be moved to a resource controller or something
	@GetMapping(path = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response) throws IOException {

		ClassPathResource imgFile = new ClassPathResource("image/Zillowlogo_200x50.gif");

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
	}

	@PostMapping(path = "/properties/search", consumes = JSON, produces = JSON)
	@ResponseBody
	public PropertySearchResult propertySearch(@RequestBody @Valid PropertySearchRequest request) throws Exception {

		return serviceProvider.propertySearch(request);

	}

	@PostMapping(path = "/properties/valuation", consumes = JSON, produces = JSON)
	@ResponseBody
	public PropertyValuationResult propertyValuation(@RequestBody @Valid PropertySearchRequest request) throws Exception {

		return serviceProvider.propertyValuation(request);

	}

	@GetMapping(path = "/users/properties", consumes = JSON, produces = JSON)
	@ResponseBody
	public Iterable<Property> getPropertiesByUser(@RequestParam String userEmail) throws Exception {

		return propertyService.getPropertyDetails(userEmail);
	}
	
	@PostMapping(path = "/users/properties", consumes = JSON, produces = JSON)
	@ResponseStatus(code = HttpStatus.CREATED)
	public <T extends Property> T createProperty(@RequestBody @Valid T property, @RequestParam String userEmail) {

		return propertyService.createProperty(userEmail, property);
	}

	@PutMapping(path = "/users/properties/{propertyId}", consumes = JSON, produces = JSON)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Property updateProperties(@PathVariable String userEmail, @RequestParam String propertyId, @Valid Property property) {

		property.setId(propertyId);
		return propertyService.updateProperty(userEmail, property);
	}

	@DeleteMapping(path = "/users/properties/{propertyId}", consumes = JSON)
	public void deleteProperty(@RequestParam String userEmail, @PathVariable String propertyId) {

		propertyService.deleteProperty(userEmail, propertyId);
	}

}
