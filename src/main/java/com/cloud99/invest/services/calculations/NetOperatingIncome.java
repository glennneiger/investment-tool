package com.cloud99.invest.services.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Net Operating Income = (Income - Expenses)
 */
public class NetOperatingIncome implements Calculation<Money> {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetOperatingIncome.class);

	@Override
	public Money calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		// TODO - NG - need to add in vacancy rate if multi-family

		// Money vacancyAmt =
		// income.getTotalAnnualOperatingIncome(currency).multipliedBy(expences.getVacancyRate());

		Money annualIncome = propertyFinances.getIncome().getTotalAnnualOperatingIncome(propertyFinances.getCurrency());
		LOGGER.debug("Income: " + annualIncome);

		Money annualExpences = propertyFinances.getExpences().getTotalAnnualOperatingExpences(propertyFinances.getCurrency());
		LOGGER.debug("Expences: " + annualExpences);

		Money noi = annualIncome.minus(annualExpences);

		LOGGER.debug("NOI: " + noi);
		return noi;
	}

}
