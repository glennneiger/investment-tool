package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;

public class Income {

	private BigDecimal deposit = new BigDecimal(0);

	private BigDecimal grossRent = new BigDecimal(0);
	private TimeUnit rentUnit = TimeUnit.MONTHY;

	private BigDecimal otherIncome = new BigDecimal(0);
	private TimeUnit otherIncomeUnit = TimeUnit.MONTHY;

	public Money getTotalIncome(CurrencyUnit currency) {
		Money total = Money.of(currency, 0);

		return total.plus(grossRent).plus(otherIncome).plus(deposit);
	}

	public Money getTotalAnnualOperatingIncome(CurrencyUnit currency) {

		return getAnnualRentalIncome(currency).plus(getAnnualOtherIncome(currency));
	}

	public Money getAnnualRentalIncome(CurrencyUnit currency) {

		return Money.of(currency, grossRent.multiply(new BigDecimal(rentUnit.getAnnualPeriods())));
	}

	public Money getAnnualOtherIncome(CurrencyUnit currency) {

		return Money.of(currency, otherIncome.multiply(new BigDecimal(otherIncomeUnit.getAnnualPeriods())));
	}

	public Money getTotalOperatingIncome(CurrencyUnit currency) {
		Money total = Money.of(currency, 0);
		return total.plus(grossRent).plus(otherIncome);
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getGrossRent() {
		return grossRent;
	}

	public void setGrossRent(BigDecimal grossRent) {
		this.grossRent = grossRent;
	}

	public TimeUnit getRentUnit() {
		return rentUnit;
	}

	public void setRentUnit(TimeUnit rentUnit) {
		this.rentUnit = rentUnit;
	}

	public BigDecimal getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}

	public TimeUnit getOtherIncomeUnit() {
		return otherIncomeUnit;
	}

	public void setOtherIncomeUnit(TimeUnit otherIncomeUnit) {
		this.otherIncomeUnit = otherIncomeUnit;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
