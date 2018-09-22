package com.cloud99.invest.integration.zillow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cloud99.invest.BaseIntegrationTest;
import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.ProviderInfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Iterator;

public class ZillowServiceProviderTest extends BaseIntegrationTest {

	@Autowired
	private ZillowServiceProvider provider;

	@Test
	public void testPropertyComp() throws Exception {
		String providerPropertyId = "13719607";
		Collection<PropertyValuationResult> result = provider.propertyCompLookup(providerPropertyId, 3);
		assertNotNull(result);
		assertTrue(result.iterator().hasNext());
		assertEquals(3, result.size());
		Iterator<PropertyValuationResult> iter = result.iterator();
		PropertyValuationResult tmpResult = null;
		do {
			tmpResult = iter.next();
			assertNotNull(tmpResult);
			assertNotNull(tmpResult.getProviderInfo());
			assertNotNull(tmpResult.getProviderId());
		} while (iter.hasNext());
	}

	@Test
	public void testPropertySearch_found() throws Exception {
		PropertySearchResult result = provider.propertySearch(buildSearchRequest());
		assertNotNull(result);
		assertNotNull(result.getProperty());
		assertNotNull(result.getProviderId());
		assertEquals(ProviderInfo.ZILLOW, result.getProviderInfo());
	}

	private PropertySearchRequest buildSearchRequest() {
		PropertySearchRequest r = new PropertySearchRequest();
		r.setAddress1("13060 west 64th place");
		r.setCity("Arvada");
		r.setState("Colorado");
		r.setZip("80004");
		return r;
	}
}
