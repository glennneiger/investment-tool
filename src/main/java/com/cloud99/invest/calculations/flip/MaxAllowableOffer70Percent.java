package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;
import com.cloud99.invest.util.Util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * MAO = (ARV * 70%) – Rehab Costs
 * https://www.reikit.com/house-flipping-guide/fix-and-flip-deal-analysis#70-percent-rule
 */
@Slf4j
public class MaxAllowableOffer70Percent implements FlipCalculation<Money> {

	private Util util = new Util();

	@Override
	public Money calculate(FlipPropertyFinances propertyFinances, Map<FlipCalculationType, FlipCalculation<?>> allCalculations, CurrencyUnit currency) {

		BigDecimal arv = propertyFinances.getAfterRepairValue();

		BigDecimal percent70 = arv.multiply(BigDecimal.valueOf(.7)).setScale(2);

		Money cashInDeal = propertyFinances.getPurchaseDetails().getTotalMoneyInDeal(currency);
		log.debug("Total cash in the deal: " + cashInDeal);

		Money allExpences = cashInDeal.plus(util.summerizeItemizedCosts(propertyFinances.getSaleClosingCosts(), (30 / propertyFinances.getHoldingDays())));
		log.debug("Total expences: " + allExpences);

		BigDecimal MAO = percent70.subtract(allExpences.getAmount()).setScale(2, RoundingMode.HALF_EVEN);
		log.debug("Maximum allowable offer: " + MAO);

		return Money.of(currency, MAO);
	}

}
