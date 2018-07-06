package com.cloud99.invest.domain;

public enum ParkingType {
	NONE("None"),
	GARAGE("Garage"),
	CAR_PORT("Car Port"),
	PRIVATE_LOT("Private Lot"),
	OFF_STREET("Off Street"),
	ON_STREET("On Street");
	
	private String name;

	private ParkingType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
