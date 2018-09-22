package com.cloud99.invest.integration;

import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ServiceProvider {

	public PropertySearchResult propertySearch(PropertySearchRequest request);

	public PropertyValuationResult propertyValuation(PropertySearchRequest request);

	public Collection<PropertyValuationResult> propertyCompLookup(String providerPropertyId, Integer numOfCompsToLookup);

}
