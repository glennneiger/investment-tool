package com.cloud99.invest.dto.responses;

import com.cloud99.invest.domain.property.Property;

import org.joda.money.CurrencyUnit;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

public class PropertyCompSearchResult implements Serializable {
	private static final long serialVersionUID = 5298328655988759158L;

	@Getter
	@Setter
	private Property subjectProperty;

	@Getter
	@Setter
	private Collection<PropertyCompValuationResult> propertyValuations;

	@Getter
	@Setter
	private CurrencyUnit currencyUnit = CurrencyUnit.USD;
}
