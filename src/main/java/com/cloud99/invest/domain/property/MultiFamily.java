package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.repo.extensions.CascadeSave;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Collection;

public class MultiFamily implements Property, MongoDocument<String> {

	@Id
	private String id;
	private String name;
	private Address address;

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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return toJsonString();
	}
}
