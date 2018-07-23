package com.cloud99.invest.services.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.util.Map;

/**
 * Cash Flow = (Net Operating Income - Debt Service)
 * 
 * Cash on Cash = (Cash Flow / Cash into Deal)
 * 
 * @param propertyFinances
 * @return
 */
public class CashOnCash implements Calculation<Double> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CashOnCash.class);

	@Override
	public Double calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		Money noi = (Money) allCalculations.get(CalculationType.NOI).calculate(propertyFinances, allCalculations);

		Money debtService = (Money) allCalculations.get(CalculationType.ANNUAL_DEBT_SERVICE).calculate(propertyFinances, allCalculations);

		Money cashFlow = noi.minus(debtService.getAmount());
		LOGGER.debug("Cash flow: " + cashFlow);

		// cash in the deal:
		// down payment, financing expenses,
		Money cashInDeal = propertyFinances.getPurchaseDetails().getTotalMoneyInDeal(propertyFinances.getCurrency());

		Money cashOnCash = cashFlow.dividedBy(cashInDeal.getAmount(), RoundingMode.HALF_EVEN);
		LOGGER.debug("Cash on cash return: " + cashOnCash);

		return cashOnCash.getAmount().doubleValue();
	}

}
