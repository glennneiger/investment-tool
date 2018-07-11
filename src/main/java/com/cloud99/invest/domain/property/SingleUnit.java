package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.TimeUnit;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public abstract class SingleUnit extends BaseProperty implements MongoDocument {

	@Id
	private String id;

	private Integer bedrooms;
	private Float bathrooms;
	private Integer finishedSqFt;
	private Integer basementSqFt;
	private Integer lotSizeSqFt;
	private Integer yearBuilt;

	private BigDecimal grossRent;
	private TimeUnit grossRentUnit;

	public BigDecimal getGrossRent() {
		return grossRent;
	}

	public void setGrossRent(BigDecimal grossRent) {
		this.grossRent = grossRent;
	}

	public TimeUnit getGrossRentUnit() {
		return grossRentUnit;
	}

	public void setGrossRentUnit(TimeUnit grossRentUnit) {
		this.grossRentUnit = grossRentUnit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBasementSqFt() {
		return basementSqFt;
	}

	public void setBasementSqFt(Integer basementSqFt) {
		this.basementSqFt = basementSqFt;
	}

	public Integer getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public Float getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(Float bathrooms) {
		this.bathrooms = bathrooms;
	}

	public Integer getFinishedSqFt() {
		return finishedSqFt;
	}

	public void setFinishedSqFt(Integer finishedSqFt) {
		this.finishedSqFt = finishedSqFt;
	}

	public Integer getLotSizeSqFt() {
		return lotSizeSqFt;
	}

	public void setLotSizeSqFt(Integer lotSizeSqFt) {
		this.lotSizeSqFt = lotSizeSqFt;
	}

	public Integer getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(Integer yearBuilt) {
		this.yearBuilt = yearBuilt;
	}

}
