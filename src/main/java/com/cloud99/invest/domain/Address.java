package com.cloud99.invest.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Address implements Serializable {
	private static final long serialVersionUID = -3350152411681350957L;

	@Getter
	@Setter
	private String address1;

	@Getter
	@Setter
	private String address2;

	@Getter
	@Setter
	private String state;

	@Getter
	@Setter
	private String city;

	@Getter
	@Setter
	private String zip;

	@Getter
	@Setter
	private Double latitude;

	@Getter
	@Setter
	private Double longitude;

	public Address() {
		super();
	}

	public Address(String address1, String state, String city, String zip) {
		this(address1, null, state, city, zip);
	}

	public Address(String address1, String address2, String state, String city, String zip) {
		this(address1, address2, state, city, zip, null, null);
	}

	public Address(String address1, String address2, String state, String city, String zip, Double latitude, Double longitude) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.state = state;
		this.city = city;
		this.zip = zip;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
