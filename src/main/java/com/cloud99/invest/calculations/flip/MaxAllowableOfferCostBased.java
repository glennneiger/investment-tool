package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;
import com.cloud99.invest.util.Util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Maximum allowable offer based on the costs of the project, not using the 70%
 * ARV rule
 */
@Slf4j
public class MaxAllowableOfferCostBased implements FlipCalculation<Money> {

	private Util util = new Util();

	@Override
	public Money calculate(FlipPropertyFinances propertyFinances, Map<FlipCalculationType, FlipCalculation<?>> allCalculations, CurrencyUnit currency) {
		
		// holding days divided by 30 gives us the number of monthly periods to calculate the total holding costs
		BigDecimal totalHoldingCosts = util.summerizeItemizedCosts(propertyFinances.getHoldingCosts(), propertyFinances.getHoldingDays() / 30);
		BigDecimal totalClosingCosts = util.summerizeItemizedCosts(propertyFinances.getPurchaseDetails().getClosingCosts(), 1);

		BigDecimal totalRehabCosts = util.summerizeItemizedCosts(propertyFinances.getPurchaseDetails().getRehabCosts(), 1);
		BigDecimal totalSalesClosingCosts = util.summerizeItemizedCosts(propertyFinances.getSaleClosingCosts(), 1);

		//@formatter:off
		BigDecimal result = 
				propertyFinances.getAfterRepairValue()
				.subtract(totalRehabCosts)
				.subtract(totalHoldingCosts)
				.subtract(totalClosingCosts)
				.subtract(totalSalesClosingCosts)
				.subtract(propertyFinances.getDesiredProfit())
				.setScale(2);
		//@formatter:on

		Money mao = Money.of(currency, result);
		log.debug("Max Allowable Offer: {}", mao);
		return mao;
	}

}
