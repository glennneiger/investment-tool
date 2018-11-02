package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonthlyDebtPayment implements RentalCalculation<Money> {

	@Override
	public Money calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency) {

		FinancingDetails loan = propertyFinances.getPurchaseDetails().getFinancingDetails();
		return calculateMonthlyPayment(loan.getInterestRate(), loan.getLoanTermYears(), loan.getLoanAmount(), currency);
	}

	private Money calculateMonthlyPayment(Float annualInterestRate, Double loanTermYears, BigDecimal loanAmount, CurrencyUnit currency) {

		double monthlyInteresteRate = annualInterestRate / 12;
		// e.g. 5% => 0.05
		monthlyInteresteRate /= 100;
		log.debug("Monthly interest rate: " + monthlyInteresteRate);

		double termInMonths = loanTermYears * 12;

		BigDecimal monthlyPayment = (loanAmount.multiply(new BigDecimal(monthlyInteresteRate)).divide(new BigDecimal((1 - Math.pow(1 + monthlyInteresteRate, -termInMonths))), 2, RoundingMode.CEILING));

		Money monthlyPaymentMoney = Money.of(currency, monthlyPayment);
		log.debug("Monthly payment: " + monthlyPaymentMoney);

		return monthlyPaymentMoney;

	}

}
