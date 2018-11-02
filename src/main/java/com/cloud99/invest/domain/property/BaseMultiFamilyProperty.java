package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.TaxAssessment;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

// TODO - NG - finish implementing multifamily data model
@Document(collection = "multiFamily")
public class BaseMultiFamilyProperty implements Property {
	private static final long serialVersionUID = -4617469255664886831L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private PropertyType propertyType;

	public BaseMultiFamilyProperty(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Address getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub

	}

	@Override
	public ParkingType getParkingType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParkingType(ParkingType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getBedRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBedRooms(Integer bedRooms) {
		// TODO Auto-generated method stub

	}

	@Override
	public Double getBathRooms() {
		// TODO Auto-generated method stub
		return 0D;
	}

	@Override
	public void setBathRooms(Double bathRooms) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getFinishedSqFt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFinishedSqFt(Integer finishedSqFt) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getLotSizeSqFt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLotSizeSqFt(Integer lotSizeSqFt) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getYearBuilt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setYearBuilt(Integer yearBuilt) {
		// TODO Auto-generated method stub

	}

	@Override
	public DateTime getLastSoldDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastSoldDate(DateTime lastSoldDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getLastSoldPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastSoldPrice(BigDecimal lastSoldPrice) {
		// TODO Auto-generated method stub

	}

	@Override
	public TaxAssessment getTaxAssessment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaxAssessment(TaxAssessment assesment) {
		// TODO Auto-generated method stub

	}
}
