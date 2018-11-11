package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;
import com.cloud99.invest.util.Util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Project Costs = Purchase Price + Purchase Costs + Holding Costs + Rehab Costs
 * (with Contingency) + Sale Costs
 */
@Slf4j
public class TotalProjectCosts implements FlipCalculation<Money> {

	private Util util = new Util();

	@Override
	public Money calculate(FlipPropertyFinances propertyFinances, Map<FlipCalculationType, FlipCalculation<?>> allCalculations, CurrencyUnit currency) {

		PurchaseDetails purchase = propertyFinances.getPurchaseDetails();

		// purchase price = 135000
		// rehab costs = 25000
		// closing costs = 13100
		// holding costs = 10000
		// sales closing costs = 5000

		Money projectCosts = purchase.getTotalMoneyInDeal(currency); // closing costs
		log.debug("Total money in the deal: " + projectCosts);

		Money salesCosts = util.summerizeItemizedCosts(currency, propertyFinances.getSaleClosingCosts());
		log.debug("Sales costs: " + salesCosts);

		// holding costs
		Money totalProjectCosts = projectCosts.plus(salesCosts).plus(propertyFinances.getPurchaseDetails().getPurchasePrice());
		log.debug("Total project costs: " + totalProjectCosts);

		return totalProjectCosts;
	}

}
