package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Debt Service Ratio = (Net Operating Income / Debt Service)
 */
@Slf4j
public class DebtServiceRatio implements RentalCalculation<BigDecimal> {

	@Override
	public BigDecimal calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency) {

		Money noi = (Money) allCalculations.get(RentalCalculationType.NOI).calculate(propertyFinances, allCalculations, currency);

		Money annualDebtService = (Money) allCalculations.get(RentalCalculationType.ANNUAL_DEBT_SERVICE).calculate(propertyFinances, allCalculations, currency);

		Money ratio = noi.dividedBy(annualDebtService.getAmount(), RoundingMode.HALF_EVEN);
		log.debug("Debt service ratio: " + ratio);

		return ratio.getAmount().setScale(2, RoundingMode.HALF_EVEN);
	}
}
