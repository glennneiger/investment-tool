package com.cloud99.invest.domain.property;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "property")
public abstract class SingleUnit extends BaseProperty {
	private static final long serialVersionUID = -6232616464839422314L;

	@Getter
	@Setter
	private Integer bedRooms;

	@Getter
	@Setter
	private Double bathRooms;

	public SingleUnit(PropertyType propertyType) {
		super(propertyType);
	}
}
