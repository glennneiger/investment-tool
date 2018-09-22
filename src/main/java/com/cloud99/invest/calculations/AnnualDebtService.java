package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AnnualDebtService implements Calculation<Money> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnnualDebtService.class);

	@Override
	public Money calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		Money monthlyPayment = (Money) allCalculations.get(CalculationType.MONTHLY_PAYMENT).calculate(propertyFinances, allCalculations);

		Money annualAmt = monthlyPayment.multipliedBy(12);
		LOGGER.debug("Annual debt service amount: " + annualAmt);
		return annualAmt;

	}

}
