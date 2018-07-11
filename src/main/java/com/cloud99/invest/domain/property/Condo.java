package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.MongoDocument;

public class Condo extends SingleUnit implements MongoDocument {

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.CONDO;
	}
}
