package com.cloud99.invest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloud99.invest.BaseIntegrationTest;
import com.cloud99.invest.DataCreator;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.PropertyType;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.dto.requests.PropertySearchRequest;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.repo.redis.AuthTokenRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

@Configuration
public class PropertyControllerIT extends BaseIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private AuthTokenRepo authTokenRepo;

	@Test
	public void testPropertySearch() throws Exception {

		String authToken = loginUser();
		PropertySearchRequest request = dataCreator.buildPropertySearchRequest();

		MvcResult result = mvc.perform(
				post("/v1/properties/search")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("authorization", authToken)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andReturn();
		
		
		String json = result.getResponse().getContentAsString();
		PropertySearchResult response = objectMapper.readValue(json, PropertySearchResult.class);
		assertNotNull(response.getProperty());
		assertEquals(ProviderInfo.ZILLOW, response.getProviderInfo());
		assertNotBlank(response.getProperty().getAddress().getAddress1());

		assertNotBlank(response.getProperty().getAddress().getCity());
		assertEqualsIgnoreCase(request.getCity(), response.getProperty().getAddress().getCity());

		assertEqualsIgnoreCase(request.getState(), response.getProperty().getAddress().getState());

		assertEqualsIgnoreCase(request.getZip(), response.getProperty().getAddress().getZip());

		assertNotNull(response.getProperty().getAddress().getLatitude());

		assertNotNull(response.getProperty().getAddress().getLongitude());

		Property property = response.getProperty();
		assertEquals(3.0, property.getBathRooms(), "Incorrect number of bathrooms received");
		assertEquals(new Integer(3), property.getBedRooms(), "Incorrect number of bedrooms received");

		assertNotNull(property.getFinishedSqFt(), "No finished square feet received");
		assertTrue(property.getFinishedSqFt() > 0, "Finished square feet is zero or less");

		assertNotNull(property.getLastSoldDate(), "No last sold date received");

		assertNotNull(property.getLastSoldPrice(), "No last sold price received");
		assertTrue(property.getLastSoldPrice().doubleValue() > 0, "Last sold price is zero or less");

		assertNotNull(property.getLotSizeSqFt(), "No lot size received");
		assertTrue(property.getLotSizeSqFt() > 0, "Lot size is zero or less");

		assertEquals(PropertyType.SINGLE_FAMILY, property.getPropertyType());

		assertNotNull(property.getTaxAssessment(), "No tax assessment received");
		assertTrue(property.getTaxAssessment().getTaxAssessment() > 0, "Tax assessment amount is zero or less");

		assertTrue(property.getTaxAssessment().getTaxYear() > 2000, "Tax year is less then 2000");

		assertNotNull(property.getYearBuilt(), "No year built received");
		assertTrue(property.getYearBuilt() > 1900, "Year built is less then 1900");

	}

	private void assertEqualsIgnoreCase(String expected, String actual) {
		if (StringUtils.isBlank(expected) || StringUtils.isBlank(actual)) {
			fail("One of the values is blank and not equals");
		} else {
			assertEquals(expected.toLowerCase(), actual.toLowerCase());
		}

	}

	private void assertNotBlank(String val) {
		assertFalse(StringUtils.isEmpty(val));
	}
	
	private String loginUser() throws Exception {
	
		MvcResult result = invokeLoginEndpoint(user.getEmail(), HttpStatus.OK);
				
		String authTokenJson = result.getResponse().getContentAsString();
		AuthToken authToken = objectMapper.readValue(authTokenJson, AuthToken.class);
	
		Mockito.when(authTokenRepo.findById(authToken.getToken())).thenReturn(Optional.of(authToken));
		return authToken.getToken();
	}
	
	private MvcResult invokeLoginEndpoint(String email, HttpStatus expectedStatus) {
		MvcResult result = null;
		try {
			result = mvc.perform(post("/public/login")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.param("userEmail", email)
					.param("password", "password"))
					.andExpect(status().is(expectedStatus.value()))
					.andReturn();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
