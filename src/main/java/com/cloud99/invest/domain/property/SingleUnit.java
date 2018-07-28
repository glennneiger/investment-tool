package com.cloud99.invest.domain.property;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.Frequency;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "property")
public abstract class SingleUnit extends BaseProperty implements MongoDocument {
	private static final long serialVersionUID = -6232616464839422314L;

	@Id
	@Getter
	@Setter
	private String id;

	// TODO - NG - add validation annotations to this
	@Getter
	@Setter
	private Integer bedrooms;

	@Getter
	@Setter
	private Float bathrooms;

	@Getter
	@Setter
	private Integer finishedSqFt;

	@Getter
	@Setter
	private Integer basementSqFt;

	@Getter
	@Setter
	private Integer lotSizeSqFt;

	@Getter
	@Setter
	private Integer yearBuilt;

	@Getter
	@Setter
	private BigDecimal grossRent;

	@Getter
	@Setter
	private Frequency grossRentUnit;

	public SingleUnit(PropertyType propertyType) {
		super(propertyType);
	}
}
