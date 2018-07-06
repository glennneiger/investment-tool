package com.cloud99.invest.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;

public class PropertySearchRequest {

	private Money highPrice;
	private Money lowPrice;

	@NotEmpty(message = "address1.required")
	private String address1;
	private String address2;

	@NotEmpty(message = "state.required")
	private String state;

	@NotEmpty(message = "city.required")
	private String city;

	@NotEmpty(message = "zip.required")
	private String zip;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Money getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Money highPrice) {
		this.highPrice = highPrice;
	}

	public Money getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Money lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
