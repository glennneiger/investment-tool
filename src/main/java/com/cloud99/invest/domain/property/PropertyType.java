package com.cloud99.invest.domain.property;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PropertyType {

	CONDO("Condo", Condo.class),
	TOWNHOUSE("Townhouse", Townhouse.class),
	SINGLE_FAMILY("Single Family", SingleFamilyProperty.class),
	MULTI_FAMILY("Multi-family", MultiFamily.class),
	OTHER("Other", SingleFamilyProperty.class);

	// TODO - NG - need to implement property class for manufactured home
	// MANUFACTURED("Manufactured"),
	// COMMERCIAL("Commercial"),
	// LAND("Land"),
	
	private String name;
	private Class<? extends Property> propertyClassType;
	
	private PropertyType(String name, Class<? extends Property> propertyClassType) {
		this.name = name;
		this.propertyClassType = propertyClassType;
	}
	
	public Class<? extends Property> getPropertyClassType() {
		return propertyClassType;
	}
	
	@Override
	public String toString() {
		return name();
	}

	@JsonCreator
	public static PropertyType fromValue(String value) {
		for (PropertyType type : values()) {
			if (type.name.equals(value)) {
				return type;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
}
