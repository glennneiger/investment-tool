package com.cloud99.invest.integration.data.zillow.domain.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditedFacts {
	private String coolingSystem;

	private String yearBuilt;

	private double bathrooms;

	private double lotSizeSqFt;

	private String coveredParkingSpaces;

	private String architecture;

	private String useCode;

	private Integer bedrooms;

	private String heatingSources;

	private String exteriorMaterial;

	private String roof;

	private String floorCovering;

	private String appliances;

	private Integer finishedSqFt;

	private String heatingSystem;

	private double rooms;
}
