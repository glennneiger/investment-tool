package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.financial.FinancialAssumptions;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

import lombok.Getter;

@Document(collection = "property")
public class SingleFamilyProperty extends SingleUnit implements Property, MongoDocument {
	private static final long serialVersionUID = -7055052204391685075L;

	@Field
	private String myName;

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public SingleFamilyProperty() {
		super(PropertyType.SINGLE_FAMILY);
	}

	@Field
	@Override
	public BigDecimal getGrossRent() {

		return super.getGrossRent();
	}

	@Override
	public void setGrossRent(BigDecimal grossRent) {

		super.setGrossRent(grossRent);
	}

	@Override
	public Frequency getGrossRentUnit() {

		return super.getGrossRentUnit();
	}

	@Override
	public void setGrossRentUnit(Frequency grossRentUnit) {

		super.setGrossRentUnit(grossRentUnit);
	}

	@Override
	public String getId() {

		return super.getId();
	}

	@Override
	public void setId(String id) {

		super.setId(id);
	}

	@Override
	public Integer getBasementSqFt() {

		return super.getBasementSqFt();
	}

	@Override
	public void setBasementSqFt(Integer basementSqFt) {

		super.setBasementSqFt(basementSqFt);
	}

	@Override
	public Integer getBedrooms() {

		return super.getBedrooms();
	}

	@Override
	public void setBedrooms(Integer bedrooms) {

		super.setBedrooms(bedrooms);
	}

	@Field
	@Override
	public Float getBathrooms() {

		return super.getBathrooms();
	}

	@Override
	public void setBathrooms(Float bathrooms) {

		super.setBathrooms(bathrooms);
	}

	@Override
	public Integer getFinishedSqFt() {

		return super.getFinishedSqFt();
	}

	@Override
	public void setFinishedSqFt(Integer finishedSqFt) {

		super.setFinishedSqFt(finishedSqFt);
	}

	@Override
	public Integer getLotSizeSqFt() {

		return super.getLotSizeSqFt();
	}

	@Override
	public void setLotSizeSqFt(Integer lotSizeSqFt) {

		super.setLotSizeSqFt(lotSizeSqFt);
	}

	@Override
	public Integer getYearBuilt() {

		return super.getYearBuilt();
	}

	@Override
	public void setYearBuilt(Integer yearBuilt) {

		super.setYearBuilt(yearBuilt);
	}

	@Override
	public void setParkingType(ParkingType type) {

		super.setParkingType(type);
	}

	@Override
	public ParkingType getParkingType() {

		return super.getParkingType();
	}

	@Override
	public String getName() {

		return super.getName();
	}

	@Override
	public void setName(String name) {

		super.setName(name);
	}

	@Override
	public Address getAddress() {

		return super.getAddress();
	}

	@Override
	public void setAddress(Address address) {

		super.setAddress(address);
	}

	@Override
	public FinancialAssumptions getFinancialAssumptions() {

		return super.getFinancialAssumptions();
	}

}
