package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.FinancialAssumptions;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "property")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "propertyType")
@JsonSubTypes({
		@JsonSubTypes.Type(value = SingleFamilyProperty.class, name = "SINGLE_FAMILY"),
		@JsonSubTypes.Type(value = Condo.class, name = "CONDO"),
		@JsonSubTypes.Type(value = Townhouse.class, name = "TOWNHOUSE"),
		@JsonSubTypes.Type(value = MultiFamily.class, name = "MULTI_FAMILY") })
public interface Property extends MongoDocument {

	public PropertyType getPropertyType();

	public String getName();

	public void setName(String name);

	public Address getAddress();

	public void setAddress(Address address);

	public ParkingType getParkingType();

	public void setParkingType(ParkingType type);

	public FinancialAssumptions getFinancialAssumptions();

	public FinancingDetails getFinancingDetails();

	public PropertyFinances getPropertyFinances();
}
