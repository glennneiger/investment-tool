package com.cloud99.invest.domain.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "property")
public class SingleFamilyProperty extends SingleUnit {
	private static final long serialVersionUID = -7055052204391685075L;

	public SingleFamilyProperty() {
		super(PropertyType.SINGLE_FAMILY);
	}
}
