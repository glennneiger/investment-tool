package com.cloud99.invest.domain.financial;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain object that represents and investors forecasted values used for
 * financial assumptions for a property.
 */
@SuppressWarnings("boxing")
public class FinancialAssumptions {

	@Getter
	@Setter
	private Float appreciationPercent = 0f;

	@Getter
	@Setter
	private Float incomeIncreasePercent = 0f;

	@Getter
	@Setter
	private Float expencesIncreasePercent = 0f;

	@Getter
	@Setter
	private Float sellingCostPercent = 0f;

	@Getter
	@Setter
	private Float vacancyRate = 0f;

	@Getter
	@Setter
	private BigDecimal landValue = new BigDecimal(0);

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
