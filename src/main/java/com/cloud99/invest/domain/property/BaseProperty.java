package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.FinancialAssumptions;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.repo.extensions.CascadeSave;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "property")
public abstract class BaseProperty implements Property {
	private static final long serialVersionUID = 1159213984877898352L;

	private String name;

	@CascadeSave
	private Address address;

	@CascadeSave
	private ParkingType parkingType;

	private PropertyType propertyType;

	@CascadeSave
	private FinancialAssumptions financialAssumptions;

	private FinancingDetails financingDetails;

	private PropertyFinances propertyFinances;

	public BaseProperty(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public FinancingDetails getFinancingDetails() {
		return financingDetails;
	}

	public void setFinancingDetails(FinancingDetails financingDetails) {
		this.financingDetails = financingDetails;
	}

	public PropertyFinances getPropertyFinances() {
		return propertyFinances;
	}

	public void setPropertyFinances(PropertyFinances propertyFinances) {
		this.propertyFinances = propertyFinances;
	}

	public void setFinancialAssumptions(FinancialAssumptions financialAssumptions) {
		this.financialAssumptions = financialAssumptions;
	}

	@Override
	public void setParkingType(ParkingType type) {
		this.parkingType = type;
	}

	@Override
	public ParkingType getParkingType() {
		return parkingType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public FinancialAssumptions getFinancialAssumptions() {
		return financialAssumptions;
	}
}
