package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.MongoDocument;

public class SingleFamilyProperty extends SingleUnit implements Property, MongoDocument {

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.SINGLE_FAMILY;
	}

}
