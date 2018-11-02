package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Net Operating Income = (Income - Expenses)
 */
@Slf4j
public class NetOperatingIncome implements RentalCalculation<Money> {

	@Override
	public Money calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency) {
		
		Money annualIncome = propertyFinances.getIncome().getTotalAnnualOperatingIncome(currency);
		log.debug("Income:\t" + annualIncome);

		Money annualExpences = propertyFinances.getExpences().getTotalAnnualOperatingExpences(currency);
		
		if (propertyFinances.getExpences().getVacancyRate() > 0) {
			float vacancyRate = propertyFinances.getExpences().getVacancyRate();
			BigDecimal vacancyAmount = annualIncome.getAmount().multiply(new BigDecimal(vacancyRate));
			vacancyAmount = vacancyAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			log.debug("Vacancy amount:\t" + vacancyAmount);
			annualExpences.plus(vacancyAmount);
		}

		log.debug("Expences:\t" + annualExpences);

		BigDecimal noi = annualIncome.getAmount().subtract(annualExpences.getAmount()).setScale(0, RoundingMode.HALF_UP);

		log.debug("NOI:\t" + noi);
		return Money.of(currency, noi);
	}

}
