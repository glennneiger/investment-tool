package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class MonthlyPayment implements Calculation<Money> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyPayment.class);

	@Override
	public Money calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		FinancingDetails loan = propertyFinances.getPurchaseDetails().getFinancingDetails();
		return calculateMonthlyPayment(loan.getInterestRate(), loan.getLoanTermYears(), loan.getLoanAmount(), propertyFinances.getCurrency());
	}

	private Money calculateMonthlyPayment(Float annualInterestRate, Double loanTermYears, BigDecimal loanAmount, CurrencyUnit currency) {

		double monthlyInteresteRate = annualInterestRate / 12;
		// e.g. 5% => 0.05
		monthlyInteresteRate /= 100;
		LOGGER.debug("Monthly interest rate: " + monthlyInteresteRate);

		double termInMonths = loanTermYears * 12;

		BigDecimal monthlyPayment = (loanAmount.multiply(new BigDecimal(monthlyInteresteRate)).divide(new BigDecimal((1 - Math.pow(1 + monthlyInteresteRate, -termInMonths))), 2, RoundingMode.CEILING));

		Money monthlyPaymentMoney = Money.of(currency, monthlyPayment);
		LOGGER.debug("Monthly payment: " + monthlyPaymentMoney);

		return monthlyPaymentMoney;

	}

}
