package com.cloud99.invest.domain.property;

public enum PropertyType {
	CONDO("Condo", Condo.class),
	TOWNHOUSE("Townhouse", Townhouse.class),
	SINGLE_FAMILY("Single Family", SingleFamilyProperty.class), 
	MULTI_FAMILY("Multi-family", MultiFamily.class);
	// TODO - NG - need to implement property class for manufactured home
	// MANUFACTURED("Manufactured"),
	// COMMERCIAL("Commercial"),
	// OTHER("Other");
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
	
	public String getName() {
		return name;
	}
}
