package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.BaseDomainObject;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.TaxAssessment;
import com.cloud99.invest.domain.financial.rental.RentalAssumptions;
import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;
import com.cloud99.invest.repo.extensions.CascadeSave;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "property")
public abstract class BaseProperty extends BaseDomainObject implements Property {
	private static final long serialVersionUID = 1159213984877898352L;

	@Id
	@Getter
	@Setter
	private String id;

	// TODO - NG - add validation annotations to this
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

	@Getter
	@Setter
	private DateTime lastSoldDate;

	@Getter
	@Setter
	private BigDecimal lastSoldPrice;

	@Getter
	@Setter
	private Integer lotSizeSqFt;

	@Getter
	@Setter
	private Integer finishedSqFt;

	@Getter
	@Setter
	private Integer basementSqFt;

	@Getter
	@Setter
	private Integer yearBuilt;

	@CascadeSave
	@Getter
	@Setter
	private RentalAssumptions financialAssumptions;

	@Getter
	@Setter
	private FinancingDetails financingDetails;

	@Getter
	@Setter
	private RentalPropertyFinances propertyFinances;

	@Getter
	@Setter
	public TaxAssessment taxAssessment;

	public BaseProperty(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

}
