package com.cloud99.invest.domain.financial;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class CashFlow {

	private Expences expences;
	private Income income;
	private FinancialAssumptions assumptions;
	private PurchaseDetails purchaseDetails;

	public CashFlow(Expences expences, Income income, PurchaseDetails purchaseDetails) {
		super();
		this.expences = expences;
		this.income = income;
		this.purchaseDetails = purchaseDetails;
	}

	public Money calculateNetOperatingIncome(CurrencyUnit currency) {
		// TODO - NG - need to add in vacancy rate if multi-family

		// Money vacancyAmt = income.getTotalAnnualOperatingIncome(currency).multipliedBy(expences.getVacancyRate());

		return income.getTotalAnnualOperatingIncome(currency).minus(expences.getTotalAnnualOperatingExpences(currency));
	}

	public Float calculateCapRate(CurrencyUnit currency) {

		return calculateNetOperatingIncome(currency).getAmount().floatValue() / purchaseDetails.getTotalPurchaseCost(currency).getAmount().floatValue();

	}

	public PurchaseDetails getPurchaseDetails() {
		return purchaseDetails;
	}

	public Expences getExpences() {
		return expences;
	}

	public Income getIncome() {
		return income;
	}

	public FinancialAssumptions getAssumptions() {
		return assumptions;
	}

}
