package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Annual Cash Flow = (Net Operating Income - Debt Service)
 * 
 * Annual Cash on Cash = (Cash Flow / Cash into Deal)
 * 
 * @param propertyFinances
 * @return
 */
@Slf4j
public class CashOnCash implements RentalCalculation<BigDecimal> {

	@Override
	public BigDecimal calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency) {

		Money noi = (Money) allCalculations.get(RentalCalculationType.NOI).calculate(propertyFinances, allCalculations, currency);

		Money debtService = (Money) allCalculations.get(RentalCalculationType.ANNUAL_DEBT_SERVICE).calculate(propertyFinances, allCalculations, currency);

		Money cashFlow = noi.minus(debtService.getAmount());
		log.debug("Cash flow: " + cashFlow);

		// cash in the deal:
		// down payment, financing expenses,
		Money cashInDeal = propertyFinances.getPurchaseDetails().getTotalMoneyInDeal(currency);
		log.debug("Total cash in the deal: " + cashInDeal);

		BigDecimal cashOnCash = cashFlow.getAmount().divide(cashInDeal.getAmount(), 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100).setScale(2));
		log.debug("Cash on cash return: " + cashOnCash);

		return cashOnCash.setScale(2);
	}
}
