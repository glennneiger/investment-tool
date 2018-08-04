package com.cloud99.invest.services.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Annual Rent / Total Price of Property (Purchase price + rehab costs)
 */
public class GrossYield implements Calculation<Double> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GrossYield.class);

	@SuppressWarnings("boxing")
	@Override
	public Double calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		BigDecimal purchase = propertyFinances.getPurchaseDetails().getPurchasePrice().add(propertyFinances.getPurchaseDetails().getTotalRehabCosts(propertyFinances.getCurrency()).getAmount());
		LOGGER.debug("Total Purchase Cost: " + purchase);
		
		Money rent = propertyFinances.getIncome().getAnnualRentalIncome(propertyFinances.getCurrency());
		LOGGER.debug("Annual Gross Rent: " + rent);
		
		BigDecimal yield = rent.getAmount().divide(purchase).multiply(new BigDecimal(100));
		yield.setScale(2, RoundingMode.HALF_EVEN);
		LOGGER.debug("Gross Yield: " + yield);

		return yield.doubleValue();
	}

}
