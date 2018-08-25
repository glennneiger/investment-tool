package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.BaseDomainObject;

import org.joda.money.Money;

import lombok.Getter;
import lombok.Setter;

/**
 * Used to capture either current or future financial valuations. If future
 * valuation is needed then the <code>FinancialAssumtions</code> should be
 * included
 */
public class PropertyValuation extends BaseDomainObject {
	private static final long serialVersionUID = -8588143174207294793L;

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

	/**
	 * Optional financial attributes used when forecasting scenarios
	 */
	@Getter
	@Setter
	private FinancialAssumptions assumptions;

}
