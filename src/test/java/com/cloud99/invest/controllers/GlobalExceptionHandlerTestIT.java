package com.cloud99.invest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloud99.invest.BaseIntegrationTest;
import com.cloud99.invest.dto.requests.AccountCreationRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class GlobalExceptionHandlerTestIT extends BaseIntegrationTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testValidationOnEndpoint() {
		
		// request DTO with no data, should fail validation
		try {
			AccountCreationRequest request = new AccountCreationRequest();

			String authToken = loginUser();

			// invoke an endpoint that uses the @Validated with an object that is missing
			// data. Assert the response
			MvcResult result = mvc.perform(
					post("/v1/accounts/")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("authorization", authToken)
					.content(objectMapper.writeValueAsString(request)))
					.andExpect(status().is4xxClientError())
					.andDo(MockMvcResultHandlers.print())
					.andReturn();

			System.out.println(result);
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println("ERROR FOO");
		}
	}

}
