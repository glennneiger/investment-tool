package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.MongoDocument;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.CurrencyUnit;
import org.springframework.data.annotation.Id;

public class PropertyFinances implements MongoDocument {
	private static final long serialVersionUID = 261293800631034168L;

	@Id
	private String id;
	private CurrencyUnit currency;
	private Expences expences;
	private Income income;
	private FinancialAssumptions assumptions;
	private PurchaseDetails purchaseDetails;

	public PropertyFinances(Expences expences, Income income, PurchaseDetails purchaseDetails, CurrencyUnit currency) {
		super();
		this.expences = expences;
		this.income = income;
		this.purchaseDetails = purchaseDetails;
		this.currency = currency;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CurrencyUnit getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyUnit currency) {
		this.currency = currency;
	}

	public void setExpences(Expences expences) {
		this.expences = expences;
	}

	public void setIncome(Income income) {
		this.income = income;
	}

	public void setAssumptions(FinancialAssumptions assumptions) {
		this.assumptions = assumptions;
	}

	public void setPurchaseDetails(PurchaseDetails purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
