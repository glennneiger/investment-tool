package com.cloud99.invest.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

public class PropertySearchRequest {

	@Getter
	@Setter
	private Money highPrice;

	@Getter
	@Setter
	private Money lowPrice;

	@Getter
	@Setter
	@NotEmpty(message = "address1.required")
	private String address1;

	@Getter
	@Setter
	private String address2;

	@Getter
	@Setter
	@NotEmpty(message = "state.required")
	private String state;

	@Getter
	@Setter
	@NotEmpty(message = "city.required")
	private String city;

	@Getter
	@Setter
	@NotEmpty(message = "zip.required")
	private String zip;

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
