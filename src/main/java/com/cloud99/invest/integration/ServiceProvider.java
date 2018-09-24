package com.cloud99.invest.integration;

import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;

import org.springframework.stereotype.Service;

@Service
public interface ServiceProvider {

	public PropertySearchResult propertySearch(PropertySearchRequest request);

	public PropertyValuationResult propertyValuation(PropertySearchRequest request);

	public PropertyCompSearchResult propertyCompLookup(String providerPropertyId, Integer numOfCompsToLookup);

}
