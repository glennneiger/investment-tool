package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.repo.extensions.CascadeSave;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Collection;

public class MultiFamily extends BaseProperty implements MongoDocument {

	@Id
	private String id;

	@DBRef
	@CascadeSave
	private Collection<SingleUnit> units = new ArrayList<>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<SingleUnit> getUnits() {
		return units;
	}

	public void setUnits(Collection<SingleUnit> units) {
		this.units = units;
	}

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.MULTI_FAMILY;
	}

}
