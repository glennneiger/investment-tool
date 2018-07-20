package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.BaseDomainObject;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.FinancialAssumptions;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.repo.extensions.CascadeSave;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "property")
public abstract class BaseProperty extends BaseDomainObject implements Property {
	private static final long serialVersionUID = 1159213984877898352L;

	@Getter
	@Setter
	private String name;

	@CascadeSave
	@Getter
	@Setter
	private Address address;

	@CascadeSave
	@Getter
	@Setter
	private ParkingType parkingType;

	@Getter
	@Setter
	private PropertyType propertyType;

	@CascadeSave
	@Getter
	@Setter
	private FinancialAssumptions financialAssumptions;

	@Getter
	@Setter
	private FinancingDetails financingDetails;

	@Getter
	@Setter
	private PropertyFinances propertyFinances;

	public BaseProperty(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

}
