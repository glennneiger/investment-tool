package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.TimeUnit;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class Townhouse extends SingleUnit implements Property, MongoDocument {

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.TOWNHOUSE;
	}
}
