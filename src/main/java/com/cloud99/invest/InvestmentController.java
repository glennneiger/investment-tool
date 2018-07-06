package com.cloud99.invest;

import com.cloud99.invest.dto.PropertySearchRequest;
import com.cloud99.invest.dto.PropertyValuationResult;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.ServiceProvider;
import com.cloud99.invest.integration.ServiceProviderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
public class InvestmentController {

	private static final String APP_JSON = "application/json";

	@Autowired
	private ServiceProviderFactory providerFactory;

	@Autowired
	private ServiceProvider serviceProvider;

	@PostConstruct
	public void init() {
		serviceProvider = providerFactory.getServiceProvider(ProviderInfo.ZILLOW);
	}

	@PostMapping(consumes = APP_JSON, produces = APP_JSON, path = "/search-property")
	@ResponseBody
	public PropertyValuationResult searchProperty(@RequestBody @Valid PropertySearchRequest request) throws Exception {

		return serviceProvider.propertyValuation(request);

	}

	public static void main(String[] args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PropertySearchRequest request = new PropertySearchRequest();
		request.setAddress1("13060 west 64th place");
		request.setCity("arvada");
		request.setState("co");
		System.out.println(mapper.writeValueAsString(request));
	}
}
