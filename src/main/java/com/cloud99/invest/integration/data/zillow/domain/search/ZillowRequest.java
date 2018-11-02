package com.cloud99.invest.integration.data.zillow.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowRequest {

	private String address;

	private String citystatezip;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCitystatezip() {
		return citystatezip;
	}

	public void setCitystatezip(String citystatezip) {
		this.citystatezip = citystatezip;
	}

	@Override
	public String toString() {
		return "ClassPojo [address = " + address + ", citystatezip = " + citystatezip + "]";
	}
}
