package com.cloud99.invest.domain.financial;

import org.joda.money.Money;

import java.math.BigDecimal;

public class FinancialAssumptions {

	private Float appreciationPercent = 0f;
	private Float incomeIncreasePercent = 0f;
	private Float expencesIncreasePercent = 0f;
	private Float sellingCostPercent = 0f;
	private Float vacancyRate = 0f;
	private BigDecimal landValue = new BigDecimal(0);

	public Float getVacancyRate() {
		return vacancyRate;
	}

	public void setVacancyRate(Float vacancyRate) {
		this.vacancyRate = vacancyRate;
	}

	public BigDecimal getLandValue() {
		return landValue;
	}

	public void setLandValue(BigDecimal landValue) {
		this.landValue = landValue;
	}

	public Float getAppreciationPercent() {
		return appreciationPercent;
	}

	public void setAppreciationPercent(Float appreciationPercent) {
		this.appreciationPercent = appreciationPercent;
	}

	public Float getIncomeIncreasePercent() {
		return incomeIncreasePercent;
	}

	public void setIncomeIncreasePercent(Float incomeIncreasePercent) {
		this.incomeIncreasePercent = incomeIncreasePercent;
	}

	public Float getExpencesIncreasePercent() {
		return expencesIncreasePercent;
	}

	public void setExpencesIncreasePercent(Float expencesIncreasePercent) {
		this.expencesIncreasePercent = expencesIncreasePercent;
	}

	public Float getSellingCostPercent() {
		return sellingCostPercent;
	}

	public void setSellingCostPercent(Float sellingCostPercent) {
		this.sellingCostPercent = sellingCostPercent;
	}

}
