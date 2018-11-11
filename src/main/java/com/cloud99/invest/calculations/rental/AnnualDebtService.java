package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Annual debt service is the monthly payment amount multiplied by 12
 */
@Slf4j
public class AnnualDebtService implements RentalCalculation<Money> {

	@Override
	public Money calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency) {

		Money monthlyPayment = (Money) allCalculations.get(RentalCalculationType.MONTHLY_PAYMENT).calculate(propertyFinances, allCalculations, currency);

		Money annualAmt = monthlyPayment.multipliedBy(12);
		log.debug("Annual debt service amount: " + annualAmt);

		return annualAmt;
	}

}
