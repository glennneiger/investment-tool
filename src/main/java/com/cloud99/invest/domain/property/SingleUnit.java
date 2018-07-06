package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.ParkingType;

import org.springframework.data.annotation.Id;

public abstract class SingleUnit implements Property, MongoDocument<String> {

	@Id
	private String id;
	private String name;
	private ParkingType parkingType;
	private Integer bedrooms;
	private Integer bathrooms;
	private Integer finishedSqFt;
	private Integer basementSqFt;
	private Integer lotSizeSqFt;
	private Integer yearBuilt;
	private Address address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBasementSqFt() {
		return basementSqFt;
	}

	public void setBasementSqFt(Integer basementSqFt) {
		this.basementSqFt = basementSqFt;
	}

	public ParkingType getParkingType() {
		return parkingType;
	}

	public void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}

	public Integer getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public Integer getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(Integer bathrooms) {
		this.bathrooms = bathrooms;
	}

	public Integer getFinishedSqFt() {
		return finishedSqFt;
	}

	public void setFinishedSqFt(Integer finishedSqFt) {
		this.finishedSqFt = finishedSqFt;
	}

	public Integer getLotSizeSqFt() {
		return lotSizeSqFt;
	}

	public void setLotSizeSqFt(Integer lotSizeSqFt) {
		this.lotSizeSqFt = lotSizeSqFt;
	}

	public Integer getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(Integer yearBuilt) {
		this.yearBuilt = yearBuilt;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
