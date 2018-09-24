package com.cloud99.invest.integration.zillow.results;

import com.cloud99.invest.integration.zillow.deserializers.AmountJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowResult {

	@Getter
	@Setter
	private ZillowEstimate zestimate;

	@Getter
	@Setter
	private ZillowEstimate rentzestimate;

	@Getter
	@Setter
	private LocalRealEstate localRealEstate;

	@Getter
	@Setter
	@JsonProperty("address")
	private ZillowAddress address;

	@Getter
	@Setter
	private Links links;

	@Getter
	@Setter
	private String zpid;

	// this value is only in the search results, not comps
	@Getter
	@Setter
	private String useCode;

	@Getter
	@Setter
	private Integer taxAssessmentYear;

	@Getter
	@Setter
	private BigDecimal taxAssessment;

	@Getter
	@Setter
	private Integer yearBuilt;

	@Getter
	@Setter
	private Integer lotSizeSqFt;

	@Getter
	@Setter
	private Integer finishedSqFt;

	@Getter
	@Setter
	private Double bathrooms;

	@Getter
	@Setter
	private Integer bedrooms;

	@Setter
	private String lastSoldDate;

	@Getter
	@Setter
	@JsonDeserialize(using = AmountJsonDeserializer.class)
	private Amount lastSoldPrice;

	@Transient
	public DateTime getLastSoldDate() {
		if (!StringUtils.isEmpty(lastSoldDate)) {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
			return formatter.parseDateTime(lastSoldDate);
		}
		return null;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
