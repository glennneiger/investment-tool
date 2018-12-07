package com.cloud99.invest.controller;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.CompAnalysisResult;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.integration.data.DataProviderInfo;
import com.cloud99.invest.integration.data.DataServiceProvider;
import com.cloud99.invest.integration.data.DataServiceProviderFactory;
import com.cloud99.invest.security.PaidMembershipAccess;
import com.cloud99.invest.services.AccountService;
import com.cloud99.invest.services.CompAnalyzerService;
import com.cloud99.invest.services.PropertyService;
import com.cloud99.invest.services.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
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

import java.io.IOException;

@RestController
// TODO FIXME - NG this should be changed to have the user own the resource: /v1/users/{userId}/accounts/{accountId}/
@RequestMapping(path = "/v1/accounts")
public class PropertyController implements Controller {

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private DataServiceProviderFactory providerFactory;

	private DataServiceProvider serviceProvider;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CompAnalyzerService compAnalyzerService;

	@Autowired
	private SecurityService securityService;

	@PostConstruct
	public void init() {
		serviceProvider = providerFactory.getServiceProvider(DataProviderInfo.ZILLOW);
	}

	@PostMapping(path = "/{accountId}/properties/search", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseBody
	public PropertySearchResult propertySearch(@RequestBody @Validated PropertySearchRequest request, @PathVariable String accountId) throws Exception {

		return serviceProvider.propertySearch(request);

	}

	@PaidMembershipAccess
	@GetMapping(path = "/{accountId}/properties/comps", produces = JSON_MEDIA_TYPE)
	@ResponseBody
	public CompAnalysisResult propertyCompLookup(@RequestParam String providerPropertyId, @PathVariable String accountId) throws Exception {

		PropertyCompSearchResult result = serviceProvider.propertyCompLookup(providerPropertyId, accountService.getAccountsGeneralSettingForCurrentUser().getNumOfCompsToLookup());
		return compAnalyzerService.compAnalysisResult(accountId, result);
	}

	@PreAuthorize("@securityService.isAccountOwner(#accountId)")
	@GetMapping(path = "/{accountId}/users/properties", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseBody
	public Iterable<Property> getPropertiesByUser(@RequestParam String userEmail, @PathVariable(name = "accountId") String accountId) throws Exception {

		return propertyService.getAllUsersPropertyDetails(userEmail, accountId);
	}
	
	@PostMapping(path = "/{accountId}/users/properties", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public <T extends Property> T createProperty(@RequestBody @Validated T property, @RequestParam String userEmail) {

		return propertyService.createProperty(userEmail, property);
	}

	@PutMapping(path = "/users/properties/{propertyId}", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Property updateProperties(@PathVariable String userEmail, @RequestParam String propertyId, @Validated Property property, @PathVariable String accountId) {

		property.setId(propertyId);
		return propertyService.updateProperty(userEmail, property);
	}

	@DeleteMapping(path = "/{accountId}/properties/{propertyId}", consumes = JSON_MEDIA_TYPE)
	public void deleteProperty(@RequestParam String userEmail, @PathVariable String propertyId, @PathVariable String accountId) {

		propertyService.deleteProperty(userEmail, propertyId);
	}

	// TODO - NG - this needs to be moved to a resource controller or something
	@GetMapping(path = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response) throws IOException {

		ClassPathResource imgFile = new ClassPathResource("image/Zillowlogo_200x50.gif");

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
	}

}
