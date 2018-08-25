package com.cloud99.invest.domain.property;

import com.cloud99.invest.repo.extensions.CascadeSave;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Collection;

public class MultiFamily extends BaseMultiFamilyProperty {
	private static final long serialVersionUID = -3298223584103684973L;

	@Id
	private String id;

	@DBRef
	@CascadeSave
	private Collection<SingleUnit> units = new ArrayList<>(0);

	public MultiFamily() {
		super(PropertyType.MULTI_FAMILY);
	}
	
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
}
