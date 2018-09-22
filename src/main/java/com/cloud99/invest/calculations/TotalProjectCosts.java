package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;

import java.util.Map;

/**
 * Project Costs = Purchase Price + Purchase Costs + Holding Costs + Rehab Costs
 * (with Contingency) + Sale Costs
 *
 */
public class TotalProjectCosts implements Calculation<Money> {

	@Override
	public Money calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		propertyFinances.getPurchaseDetails().getTotalMoneyInDeal(propertyFinances.getCurrency());
		return null;
	}

}
