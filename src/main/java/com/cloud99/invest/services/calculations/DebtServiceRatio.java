package com.cloud99.invest.services.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.util.Map;

/**
 * Debt Service Ratio = (Net Operating Income / Debt Service)
 */
public class DebtServiceRatio implements Calculation<Double> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtServiceRatio.class);

	@Override
	public Double calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		Money noi = (Money) allCalculations.get(CalculationType.NOI).calculate(propertyFinances, allCalculations);

		Money annualDebtService = (Money) allCalculations.get(CalculationType.ANNUAL_DEBT_SERVICE).calculate(propertyFinances, allCalculations);

		Money ratio = noi.dividedBy(annualDebtService.getAmount(), RoundingMode.HALF_EVEN);
		LOGGER.debug("Debt service ratio: " + ratio);

		return ratio.getAmount().doubleValue();
	}

}
