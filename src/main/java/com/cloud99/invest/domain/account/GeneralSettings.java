package com.cloud99.invest.domain.account;

import com.cloud99.invest.converters.json.CurrencyUnitDeserializer;
import com.cloud99.invest.converters.json.CurrencyUnitSerializer;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.joda.money.CurrencyUnit;

import javax.validation.constraints.Max;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to store general account settings including any app usage
 * limits or restrictions
 */
public class GeneralSettings implements Serializable {
	private static final long serialVersionUID = -2336061599993466226L;

	@Setter
	@Getter
	private boolean allowedToStoreDocuments = false;
	
	// -1 = infinity, no limit on the number of properties the user can store
	@Getter
	@Setter
	private Integer numberOfPropertiesUserCanStore;

	@Getter
	@Setter
	private MembershipType membershipType;

	// The default value is 3 for FREE account, up to 6 for PAID accounts
	@Getter
	@Setter
	@Max(value = 6, message = "number.of.comps.exceded")
	private Integer numOfCompsToLookup = 3;

	@JsonSerialize(using = CurrencyUnitSerializer.class)
	@JsonDeserialize(using = CurrencyUnitDeserializer.class)
	@Setter
	@Getter
	private CurrencyUnit currency = CurrencyUnit.USD;


	@Getter
	@Setter
	private List<ItemizedCost> holdingCostList;

	@Getter
	@Setter
	private List<ItemizedCost> expencesList;

	@Getter
	@Setter
	private List<ItemizedCost> closingCostsList;

	@Setter
	@Getter
	private Locale locale;
}
