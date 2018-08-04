package com.cloud99.invest.services.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Annual Cash Flow = (Net Operating Income - Debt Service)
 * 
 * Annual Cash on Cash = (Cash Flow / Cash into Deal)
 * 
 * @param propertyFinances
 * @return
 */
public class CashOnCash implements Calculation<BigDecimal> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CashOnCash.class);

	@Override
	public BigDecimal calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		Money noi = (Money) allCalculations.get(CalculationType.NOI).calculate(propertyFinances, allCalculations);

		Money debtService = (Money) allCalculations.get(CalculationType.ANNUAL_DEBT_SERVICE).calculate(propertyFinances, allCalculations);

		Money cashFlow = noi.minus(debtService.getAmount());
		LOGGER.debug("Cash flow: " + cashFlow);

		// cash in the deal:
		// down payment, financing expenses,
		Money cashInDeal = propertyFinances.getPurchaseDetails().getTotalMoneyInDeal(propertyFinances.getCurrency());

		Money cashOnCash = cashFlow.dividedBy(cashInDeal.getAmount(), RoundingMode.HALF_EVEN).multipliedBy(100);
		LOGGER.debug("Cash on cash return: " + cashOnCash);

		return cashOnCash.getAmount().setScale(2, RoundingMode.HALF_EVEN);
	}
}
