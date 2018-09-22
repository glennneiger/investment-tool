package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Net Operating Income = (Income - Expenses)
 */
public class NetOperatingIncome implements Calculation<Money> {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetOperatingIncome.class);

	@Override
	public Money calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {
		
		Money annualIncome = propertyFinances.getIncome().getTotalAnnualOperatingIncome(propertyFinances.getCurrency());
		LOGGER.debug("Income:\t" + annualIncome);

		Money annualExpences = propertyFinances.getExpences().getTotalAnnualOperatingExpences(propertyFinances.getCurrency());
		
		if (propertyFinances.getExpences().getVacancyRate() > 0) {
			float vacancyRate = propertyFinances.getExpences().getVacancyRate();
			BigDecimal vacancyAmount = annualIncome.getAmount().multiply(new BigDecimal(vacancyRate));
			vacancyAmount = vacancyAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			LOGGER.debug("Vacancy amount:\t" + vacancyAmount);
			annualExpences.plus(vacancyAmount);
		}

		LOGGER.debug("Expences:\t" + annualExpences);

		Money noi = annualIncome.minus(annualExpences);

		LOGGER.debug("NOI:\t" + noi);
		return noi;
	}

}
