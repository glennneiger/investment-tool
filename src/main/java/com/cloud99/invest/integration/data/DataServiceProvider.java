package com.cloud99.invest.integration.data;

import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertySearchResult;

import org.springframework.stereotype.Service;

@Service
public interface DataServiceProvider {

	public PropertySearchResult propertySearch(PropertySearchRequest request);

	public PropertyCompSearchResult propertyCompLookup(String providerPropertyId, Integer numOfCompsToLookup);

}
