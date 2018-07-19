package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.MongoDocument;

public class Condo extends SingleUnit implements MongoDocument {
	private static final long serialVersionUID = -7733150777786776027L;

	public Condo() {
		super(PropertyType.CONDO);
	}
}
