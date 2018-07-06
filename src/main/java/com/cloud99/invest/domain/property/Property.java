package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;

public interface Property {

	public PropertyType getPropertyType();

	public String getName();

	public Address getAddress();
}
