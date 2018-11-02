package com.cloud99.invest.integration.data.zillow.domain.property;

import com.cloud99.invest.integration.data.zillow.domain.ZillowAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
	private String highSchool;

	private PageViewCount pageViewCount;

	private String schoolDistrict;

	private ZillowAddress address;

	private String middleSchool;

	private String elementarySchool;

	private Images images;

	private String homeDescription;

	private Links links;

	private EditedFacts editedFacts;

	private String zpid;
}
