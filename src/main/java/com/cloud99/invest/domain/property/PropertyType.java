package com.cloud99.invest.domain.property;

public enum PropertyType {
	CONDO("Condo"),
	TOWNHOUSE("Townhouse"),
	SINGLE_FAMILY("Single Family"), 
	MANUFACTURED("Manuafactured"),
	MULTI_FAMILY("Multi-family"), 
	COMMERCIAL("Commercial"), 
	LAND("Land"),
	OTHER("Other");
	
	private String name;
	private PropertyType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
