package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.ParkingType;
import com.cloud99.invest.domain.financial.TaxAssessment;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "propertyType")
@Document(collection = "property")
@JsonSubTypes({
		@JsonSubTypes.Type(value = SingleFamilyProperty.class, name = "SINGLE_FAMILY"),
		@JsonSubTypes.Type(value = Condo.class, name = "CONDO"),
		@JsonSubTypes.Type(value = Townhouse.class, name = "TOWNHOUSE"),
		@JsonSubTypes.Type(value = MultiFamily.class, name = "MULTI_FAMILY") })
public interface Property extends MongoDocument {

	public String getId();

	public void setId(String id);

	public PropertyType getPropertyType();

	public String getName();

	public void setName(String name);

	public Address getAddress();

	public void setAddress(Address address);

	public ParkingType getParkingType();

	public void setParkingType(ParkingType type);

	public Integer getBedRooms();

	public void setBedRooms(Integer bedRooms);

	public Double getBathRooms();

	public void setBathRooms(Double bathRooms);

	public Integer getFinishedSqFt();

	public void setFinishedSqFt(Integer finishedSqFt);

	public Integer getLotSizeSqFt();

	public void setLotSizeSqFt(Integer lotSizeSqFt);

	public Integer getYearBuilt();

	public void setYearBuilt(Integer yearBuilt);

	public DateTime getLastSoldDate();

	public void setLastSoldDate(DateTime lastSoldDate);

	public BigDecimal getLastSoldPrice();

	public void setLastSoldPrice(BigDecimal lastSoldPrice);

	/*
	 * Financial Data
	 */

	public TaxAssessment getTaxAssessment();

	public void setTaxAssessment(TaxAssessment assesment);

}
