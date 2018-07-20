package com.cloud99.invest.services;

import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.property.Property;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

public class AnalyzerService {

	@Autowired
	private PropertyService propertyService;

	@Cacheable(cacheNames = { "investment" }, key = "#propertyId.concat('-noi')")
	public Money calculateNetOperatingIncome(String propertyId) {

		Property property = propertyService.getProperty(propertyId);

		PropertyFinances propertyFinances = property.getPropertyFinances();

		// TODO - NG - need to add in vacancy rate if multi-family

		// Money vacancyAmt =
		// income.getTotalAnnualOperatingIncome(currency).multipliedBy(expences.getVacancyRate());

		return propertyFinances.getIncome().getTotalAnnualOperatingIncome(propertyFinances.getCurrency()).minus(propertyFinances.getExpences().getTotalAnnualOperatingExpences(propertyFinances.getCurrency()));
	}

	@Cacheable(cacheNames = { "investment" }, key = "#propertyId.concat('-capRate')")
	public Float calculateCapRate(String propertyId) {

		Property property = propertyService.getProperty(propertyId);
		PropertyFinances cashFlow = property.getPropertyFinances();
		return calculateNetOperatingIncome(propertyId).getAmount().floatValue() / cashFlow.getPurchaseDetails().getTotalPurchaseCost(cashFlow.getCurrency()).getAmount().floatValue();

	}
}
