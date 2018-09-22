package com.cloud99.invest.domain.account;

import com.cloud99.invest.converters.json.CurrencyUnitDeserializer;
import com.cloud99.invest.converters.json.CurrencyUnitSerializer;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.joda.money.CurrencyUnit;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to store general account settings including any app usage
 * limits or restrictions
 */
public class GeneralSettings implements Serializable {
	private static final long serialVersionUID = -2336061599993466226L;

	// tracks how many documents an individual account has associated with it
	@Getter
	@Setter
	private Integer storedDocumentCount = 0;

	@Setter
	@Getter
	private Integer totalDocsAllowed = 5; // -1 means infinity
	
	@JsonSerialize(using = CurrencyUnitSerializer.class)
	@JsonDeserialize(using = CurrencyUnitDeserializer.class)
	@Setter
	@Getter
	private CurrencyUnit defaultCurrency = CurrencyUnit.USD;

	@Getter
	@Setter
	private Integer numOfCompsToLookup = 3;
}
