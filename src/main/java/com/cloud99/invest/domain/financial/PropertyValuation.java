package com.cloud99.invest.domain.financial;

import com.cloud99.invest.converters.json.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.joda.money.Money;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Used to capture either current or future financial valuations. If future
 * valuation is needed then the <code>FinancialAssumtions</code> should be
 * included
 */
public class PropertyValuation implements Serializable {
	private static final long serialVersionUID = -8588143174207294793L;

	@JsonSerialize(using = MoneySerializer.class)
	@Getter
	@Setter
	private Money currentEstimate;

	@Getter
	@Setter
	private Money valueChange;

	@Getter
	@Setter
	private Integer durationDays;

	@Getter
	@Setter
	private Integer duration;

	@Getter
	@Setter
	private Money highValue;

	@Getter
	@Setter
	private Money lowValue;

	@Getter
	@Setter
	private Double percentileChange;

}
