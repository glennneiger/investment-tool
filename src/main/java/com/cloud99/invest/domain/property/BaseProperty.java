package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.FinancialAssumptions;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "property")
public abstract class BaseProperty implements Property {

	private String name;

	private Address address;

	private ParkingType parkingType;

	private FinancialAssumptions financialAssumptions;

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
