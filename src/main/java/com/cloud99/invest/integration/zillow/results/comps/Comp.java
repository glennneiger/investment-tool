package com.cloud99.invest.integration.zillow.results.comps;

import com.cloud99.invest.integration.zillow.results.LastSoldPrice;
import com.cloud99.invest.integration.zillow.results.Links;
import com.cloud99.invest.integration.zillow.results.LocalRealEstate;
import com.cloud99.invest.integration.zillow.results.ZillowAddress;
import com.cloud99.invest.integration.zillow.results.ZillowEstimate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Comp {

	@Setter
	@Getter
	private ZillowEstimate zestimate;
	@Setter
	@Getter
	private String lastSoldDate;
	@Setter
	@Getter
	private String yearBuilt;
	@Setter
	@Getter
	private Double score;
	@Setter
	@Getter
	private Double bathrooms;
	@Setter
	@Getter
	private Long taxAssessment;
	@Setter
	@Getter
	private Integer lotSizeSqFt;
	@Setter
	@Getter
	private Links links;
	@Setter
	@Getter
	private Integer bedrooms;
	@Setter
	@Getter
	private Integer taxAssessmentYear;
	@Setter
	@Getter
	private LocalRealEstate localRealEstate;
	@Setter
	@Getter
	private ZillowAddress address;
	@Setter
	@Getter
	private LastSoldPrice lastSoldPrice;
	@Setter
	@Getter
	private String finishedSqFt;
	@Setter
	@Getter
	private String zpid;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
