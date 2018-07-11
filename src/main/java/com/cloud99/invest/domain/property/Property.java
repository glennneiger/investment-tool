package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.FinancialAssumptions;

public interface Property extends MongoDocument {

	public PropertyType getPropertyType();

	public String getName();

	public void setName(String name);

	public Address getAddress();

	public void setAddress(Address address);

	public ParkingType getParkingType();

	public void setParkingType(ParkingType type);

	public FinancialAssumptions getFinancialAssumptions();
}
