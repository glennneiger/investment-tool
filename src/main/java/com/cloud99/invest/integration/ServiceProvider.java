package com.cloud99.invest.integration;

import com.cloud99.invest.dto.PropertySearchRequest;
import com.cloud99.invest.dto.PropertyValuationResult;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ServiceProvider {

	public PropertyValuationResult propertyValuation(PropertySearchRequest request) throws Exception;

	public Collection<PropertyValuationResult> propertyCompLookup(PropertySearchRequest request);
}
