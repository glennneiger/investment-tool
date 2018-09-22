package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Monthly Rent / Total Price of Property (purchase price + rehab costs)
 */
public class RentCost implements Calculation<Double> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RentCost.class);

	@Override
	public Double calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		BigDecimal rent = propertyFinances.getIncome().getGrossRent();
		LOGGER.debug("Monthly Rent: " + rent);

		BigDecimal purchasePrice = propertyFinances.getPurchaseDetails().getPurchasePrice();
		LOGGER.debug("Purchase Price: " + purchasePrice);

		Money rehab = propertyFinances.getPurchaseDetails().getTotalRehabCosts(propertyFinances.getCurrency());
		LOGGER.debug("Rehab Costs: " + rehab);

		Money expences = rehab.plus(purchasePrice);
		LOGGER.debug("Total Expences: " + expences);

		BigDecimal rentCost = rent.divide(expences.getAmount(), 4, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100));
		LOGGER.debug("Rent Cost: " + rentCost);

		return rentCost.doubleValue();
	}

}
