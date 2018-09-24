package com.cloud99.invest.integration.zillow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.cloud99.invest.BaseMockitoTest;
import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

public class ZillowServiceProviderTest extends BaseMockitoTest {

	@InjectMocks
	private ZillowServiceProvider provider = new ZillowServiceProvider();

	@Mock
	private RestTemplate restTemplate;

	private FileUtil fileUtil = new FileUtil();
	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void beforeEach() {
		provider.init();
	}

	@AfterEach
	public void afterEach() {
		Mockito.reset(restTemplate);
	}

	@Test
	public void testPropertyComp() throws Exception {

		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyMap())).thenReturn(getCompTestData("ZillowCompsResultSample.xml"));
		
		String providerPropertyId = "13719607";
		PropertyCompSearchResult result = provider.propertyCompLookup(providerPropertyId, 2);
		System.err.println(objectMapper.writeValueAsString(result));
		assertNotNull(result);
		assertEquals(2, result.getPropertyValuations().size());
		assertNotNull(result.getSubjectProperty());

		for (PropertyValuationResult tmpResult : result.getPropertyValuations()) {
			assertNotNull(tmpResult);
			assertNotNull(tmpResult.getProviderInfo());
			assertNotNull(tmpResult.getProviderId());
			assertNotNull(tmpResult.getCurrentEstimate());
		}
	}

	private String getCompTestData(String testFileName) throws Exception {
		return fileUtil.getFileContents(testFileName);
	}

	@Test
	public void testPropertySearch_found() throws Exception {

		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyMap())).thenReturn(getCompTestData("ZillowSearchResultsSample.xml"));

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
