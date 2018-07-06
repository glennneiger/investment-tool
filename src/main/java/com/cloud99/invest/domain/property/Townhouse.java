package com.cloud99.invest.domain.property;

public class Townhouse extends SingleUnit implements Property {

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.TOWNHOUSE;
	}

	@Override
	public String toString() {
		return toJsonString();
	}
}
