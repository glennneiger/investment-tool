package com.cloud99.invest.services;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;

public class AnalyzerService {

	public Money calculateNetOperatingIncome(PropertyFinances cashFlow) {
		// TODO - NG - need to add in vacancy rate if multi-family

		// Money vacancyAmt =
		// income.getTotalAnnualOperatingIncome(currency).multipliedBy(expences.getVacancyRate());

		return cashFlow.getIncome().getTotalAnnualOperatingIncome(cashFlow.getCurrency()).minus(cashFlow.getExpences().getTotalAnnualOperatingExpences(cashFlow.getCurrency()));
	}

	public Float calculateCapRate(PropertyFinances cashFlow) {

		return calculateNetOperatingIncome(cashFlow).getAmount().floatValue() / cashFlow.getPurchaseDetails().getTotalPurchaseCost(cashFlow.getCurrency()).getAmount().floatValue();

	}
}
