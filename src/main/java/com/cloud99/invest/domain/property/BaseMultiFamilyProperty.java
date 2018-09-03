package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.FinancialAssumptions;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.financial.TaxAssessment;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

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
	public float getBathRooms() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBathRooms(float bathRooms) {
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

	@Override
	public FinancialAssumptions getFinancialAssumptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinancingDetails getFinancingDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFinancingDetails(FinancingDetails details) {
		// TODO Auto-generated method stub

	}

	@Override
	public PropertyFinances getPropertyFinances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPropertyFinances(PropertyFinances finances) {
		// TODO Auto-generated method stub

	}
}
