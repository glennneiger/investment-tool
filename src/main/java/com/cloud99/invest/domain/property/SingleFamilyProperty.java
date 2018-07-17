package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.MongoDocument;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "property")
@TypeAlias("SingleFamilyProperty")
public class SingleFamilyProperty extends SingleUnit implements Property, MongoDocument {
	private static final long serialVersionUID = -7055052204391685075L;

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.SINGLE_FAMILY;
	}

}
