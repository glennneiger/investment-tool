package com.cloud99.invest.integration.data.zillow.domain.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Images {
	private String count;

	private Image image;
}
