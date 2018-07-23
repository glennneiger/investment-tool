package com.cloud99.invest.services.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Cap Rate = (NOI / Total Purchase Price [including rehab costs])
 */
public class CapRate implements Calculation<Double> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtServiceRatio.class);

	@Override
	public Double calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		Money noi = (Money) allCalculations.get(CalculationType.NOI).calculate(propertyFinances, allCalculations);
		Float purchasePrice = propertyFinances.getPurchaseDetails().getTotalPurchaseCost(propertyFinances.getCurrency()).getAmount().floatValue();
		Double capRate = noi.getAmount().doubleValue() / purchasePrice;
		LOGGER.debug("Cap Rate: " + capRate);

		return capRate;

	}

}
