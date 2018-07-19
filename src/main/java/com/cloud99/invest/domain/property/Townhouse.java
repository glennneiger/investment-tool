package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.MongoDocument;

public class Townhouse extends SingleUnit implements Property, MongoDocument {
	private static final long serialVersionUID = 2550455741702423550L;

	public Townhouse() {
		super(PropertyType.TOWNHOUSE);
	}

}
