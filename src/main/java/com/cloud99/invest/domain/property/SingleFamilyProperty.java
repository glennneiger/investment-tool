package com.cloud99.invest.domain.property;

public class SingleFamilyProperty extends SingleUnit implements Property {

	private PropertyType propertyType = PropertyType.SINGLE_FAMILY;

	@Override
	public PropertyType getPropertyType() {
		return propertyType;
	}

	@Override
	public String toString() {
		return toJsonString();
	}
}
