package com.cloud99.invest.domain;

import org.springframework.data.annotation.Id;

public class Address implements MongoDocument {

	@Id
	private String id;
	private String address1;
	private String address2;
	private String state;
	private String city;
	private String zip;
	private Double latitude;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

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

	@Override
	public String toString() {
		return toJsonString();
	}
}
