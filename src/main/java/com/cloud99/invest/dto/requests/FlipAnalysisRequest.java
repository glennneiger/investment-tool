package com.cloud99.invest.dto.requests;

import com.cloud99.invest.domain.financial.ItemizedCost;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.CurrencyUnit;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

public class FlipAnalysisRequest implements Serializable {
	private static final long serialVersionUID = 7794122319234661351L;

	@Getter
	@Setter
	private CurrencyUnit currencyUnit = CurrencyUnit.USD;

	@NotNull(message = "after.repair.value.required")
	@Getter
	@Setter
	private BigDecimal afterRepairValue = BigDecimal.valueOf(0);

	@NotNull(message = "desired.profit.required")
	@Getter
	@Setter
	private BigDecimal desiredProfit = BigDecimal.valueOf(0);

	@Getter
	@Setter
	private BigDecimal repairCosts = BigDecimal.valueOf(0);

	@NotNull(message = "holding.days.required")
	@Getter
	@Setter
	private Integer holdingDays;

	@Getter
	@Setter
	private Collection<ItemizedCost> holdingCosts = new ArrayList<>(0);

	@Getter
	@Setter
	private Collection<ItemizedCost> closingCosts = new ArrayList<>(0);

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
