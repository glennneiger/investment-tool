package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Cap Rate = (NOI / Total Purchase Price [including rehab costs])
 */
public class CapRate implements Calculation<BigDecimal> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtServiceRatio.class);

	@Override
	public BigDecimal calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		Money noi = (Money) allCalculations.get(CalculationType.NOI).calculate(propertyFinances, allCalculations);
		Float purchasePrice = propertyFinances.getPurchaseDetails().getTotalPurchaseCost(propertyFinances.getCurrency()).getAmount().floatValue();
		BigDecimal capRate = BigDecimal.valueOf(noi.getAmount().doubleValue() / purchasePrice * 100).setScale(4, RoundingMode.HALF_EVEN);
		LOGGER.debug("Cap Rate: " + capRate);

		return capRate;

	}

}
