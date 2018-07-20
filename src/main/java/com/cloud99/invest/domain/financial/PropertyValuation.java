package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.BaseDomainObject;
import com.cloud99.invest.domain.MongoDocument;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.money.Money;

import lombok.Getter;
import lombok.Setter;

/**
 * Used for capturing detailed valuations for third-party integrations like
 * zillow
 */
public class PropertyValuation extends BaseDomainObject implements MongoDocument {

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private Money currentEstimate;

	@Getter
	@Setter
	private Money valueChange;

	@Getter
	@Setter
	private Money highValue;

	@Getter
	@Setter
	private Money lowValue;

	@Getter
	@Setter
	private Float percentileChange;

	@Getter
	@Setter
	private FinancialAssumptions assumptions;

}
