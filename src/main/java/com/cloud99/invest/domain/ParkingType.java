package com.cloud99.invest.domain;

/**
 * Defines the parking type a property has
 */
public enum ParkingType {
	NONE("None"),
	GARAGE("Garage"),
	CAR_PORT("Car Port"),
	PRIVATE_LOT("Private Lot"),
	OFF_STREET("Off Street"),
	ON_STREET("On Street");
	
	private final String name;

	private ParkingType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
